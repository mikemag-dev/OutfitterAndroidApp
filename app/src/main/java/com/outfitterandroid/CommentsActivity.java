package com.outfitterandroid;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Created by MaguireM on 4/22/15.
 *
 * This class involves functionality for commenting. Has a comments adapter for the customized
 * comment item view. Also has a comparator by which to sort the comments. By defualt it sorts
 * most upvotes to least upvotes and then least recent to most recent.
 *
 */
public class CommentsActivity extends Activity{
    public static final String EXTRA_SUBMISSION_ID = "com.outfitterandroid.submissionId";
    private static final String TAG = "CommentsActivity";

    final String COMMENT_EMPTY = "comment text is empty";
    final String COMMENT_NAUGHTY = "that is a naughty word";

    String mSubmissionId;
    List<ParseObject> mComments;
    ListView mCommentsListView;
    EditText mCommentEditText;
    Button mAddCommentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        mSubmissionId = getIntent().getStringExtra(EXTRA_SUBMISSION_ID);

        mCommentsListView = (ListView) findViewById(R.id.comments_list_view);
        mCommentEditText = (EditText) findViewById(R.id.comment_edit_text);
        mAddCommentButton = (Button) findViewById(R.id.add_comment_button);
        mComments = getComments();
        mComments = sortComments(mComments);
        mCommentsListView.setAdapter(new CommentsAdapter(mSubmissionId, mComments));
        mAddCommentButton.setOnClickListener(addComment());
    }

    /**
     * private function return
     * @return a list of parse objects representing the comments associated with
     * the current submission
     */
    private List<ParseObject> getComments() {
        List<ParseObject> comments = null;
        try {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Comment");
            query.whereEqualTo("submissionId", mSubmissionId);
            comments = query.find();
            for(ParseObject comment : comments){
                comment.put("numUpvotes", getNumUpvotes(comment.getObjectId())) ;
            }
        }
        catch(ParseException e){
            Log.d(TAG, "bad comment save");
        }
        return comments;
    }

    /**
     * @param comments list of comments to be sorted
     * @return comments sorted first by number of upvotes then in chronological order from
     * least recent to most
     */
    private List<ParseObject> sortComments(List<ParseObject> comments) {
        if (null != comments) Collections.sort(comments, new CommentComparator());
        return comments;
    }

    private View.OnClickListener addComment() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = mCommentEditText.getText().toString();
                if (commentText.isEmpty()) {
                    Toast.makeText(CommentsActivity.this, COMMENT_EMPTY, Toast.LENGTH_SHORT).show();
                }
                else if (isNaughty(commentText)){
                    Toast.makeText(CommentsActivity.this, COMMENT_NAUGHTY, Toast.LENGTH_SHORT).show();
                }
                else{
                    mCommentEditText.setText("");
                    hideKeyboard();
                    ParseObject comment = new ParseObject("Comment");
                    comment.put("comment", commentText);
                    //comment.put("numUpvotes", 0);
                    comment.put("submissionId", mSubmissionId);
                    comment.put("userId", ParseUser.getCurrentUser().getObjectId());
                    try {
                        comment.save();
                        comment.put("numUpvotes", 0);
                        mComments.add(comment);
                        CommentsAdapter ca = (CommentsAdapter) mCommentsListView.getAdapter();
                        ca.notifyDataSetChanged();
                    }
                    catch(ParseException e){
                        Log.d(TAG, "bad comment save");
                    }
                }
            }
        };
    }

    /**
     * @param commentText text to be analyze
     * @return true if comment text contains word from prohibited list
     */
    private boolean isNaughty(String commentText) {
        if (commentText.contains("fuck") ||
                commentText.contains("suck") ||
                commentText.contains("shit") ||
                commentText.contains("dick") ||
                commentText.contains("cock") ||
                commentText.contains("gay") ||
                commentText.contains("fag")){
            return true;
        }
        else return false;

    }

    /**
     * hides on screen keyboard
     */
    private void hideKeyboard() {
        View focusedView = CommentsActivity.this.getCurrentFocus();
        if (null != focusedView){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(focusedView.getWindowToken(), 0);
        }
    }

    private int getNumUpvotes(String commentId) {
        ParseQuery<ParseObject> query = ParseQuery.getQuery("CommentActivity");
        query.whereEqualTo("commentId", commentId);
        try {
            int numUpvotes = query.find().size();
            Log.d(TAG, String.format("commentId : %s\tnumUpvotes : %d", commentId, numUpvotes));
            return numUpvotes;
        }
        catch (ParseException e){
            Log.d(TAG, "bad commentId");
            return 0;
        }
    }

    public class CommentsAdapter extends BaseAdapter {
        private static final String TAG = "CommentsAdapter";

        String mSubmissionId;
        ParseObject mSubmission;
        List<ParseObject> mComments;


        public CommentsAdapter(String submissionId, List<ParseObject> comments) {
            mSubmissionId = submissionId;
            try{
                ParseQuery<ParseObject> query = ParseQuery.getQuery("Submission");
                query.whereEqualTo("submissionId", mSubmissionId);
                mSubmission = query.getFirst();
            }
            catch (ParseException e){
                Log.d(TAG, String.format("bad submissionId %s", submissionId));
            }
            mComments = comments;
        }

        @Override
        public int getCount() {
            return mComments.size();
        }

        @Override
        public Object getItem(int position) {
            if (null == mComments) {
                return null;
            }
            else{
                return mComments.get(position);
            }
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            if (null == convertView){
                convertView = getLayoutInflater().inflate(R.layout.comment_element, null);
            }

            Button deleteCommentButton = (Button) convertView.findViewById(R.id.delete_comment_button);

            //collect data
            final ParseObject comment = (ParseObject) getItem(position);
            String commentId = comment.getObjectId();
            String commentText = comment.getString("comment");
            String numUpvotes = Integer.toString(comment.getInt("numUpvotes"));
            String userId, username;
            userId = username = comment.getString("userId");
            SimpleDateFormat dt = new SimpleDateFormat("hh:mm MM-dd-yyyy");
            String createdAt = dt.format(comment.getCreatedAt());
            try {

                ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
                username = query.get(userId).getString("username");
            }
            catch (ParseException e){
                Log.d(TAG, String.format("bad user objectId %s", userId));
            }

            //fill views
            TextView numUpvotesTextView = (TextView) convertView.findViewById(R.id.num_upvotes_text_view);
            TextView commenterCommentTextView = (TextView) convertView.findViewById(R.id.commenter_comment_text_view);
            TextView commenterUsernameTextView = (TextView) convertView.findViewById(R.id.commenter_username_text_view);
            TextView commenterCommentCreatedAtTextView = (TextView) convertView.findViewById(R.id.commenter_created_at_text_view);
            LinearLayout upvoteLinearLayout = (LinearLayout) convertView.findViewById(R.id.upvote_linear_layout);

            numUpvotesTextView.setText(numUpvotes);
            commenterCommentTextView.setText(commentText);
            commenterUsernameTextView.setText(username);
            commenterCommentCreatedAtTextView.setText(createdAt);
            upvoteLinearLayout.setOnClickListener(trySubmitCommentUpvote(comment));
            //don't show delete button if user not authorized to delete
            if (isAuthorizedToDelete(comment)){
                deleteCommentButton.setOnClickListener(tryDeleteComment(position, comment));
            }
            else{
                deleteCommentButton.setVisibility(View.INVISIBLE);
            }


            convertView.setClickable(false);
            return convertView;
        }

        private View.OnClickListener tryDeleteComment(final int position, final ParseObject comment) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        comment.delete();
                        mComments.remove(position);
                        notifyDataSetChanged();
                    }
                    catch (ParseException e){
                        Log.d(TAG, "could not delete comment");
                    }
                }
            };
        }

        private boolean isAuthorizedToDelete(ParseObject comment) {
            ParseUser curUser = ParseUser.getCurrentUser();
            if (comment.getString("userId").equals(curUser.getObjectId())||
                mSubmission.getString("submittedByUser").equals(curUser.getObjectId())||
                mSubmission.getString("submittedByUser").equals(curUser.getString("name"))) {
                return true;
            }
            return false;
        }

        private View.OnClickListener trySubmitCommentUpvote(final ParseObject comment) {
            return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "upvote clicked");
                    String commentId = comment.getObjectId();
                    String userId = ParseUser.getCurrentUser().getObjectId();
                    if (hasNotVoted(userId, commentId)) {
                        ParseObject upvote = new ParseObject("CommentActivity");
                        upvote.put("commentId", commentId);
                        upvote.put("userId", userId);
                        try {
                            upvote.save();
                            Log.d(TAG, "upvote saved");
                            comment.put("numUpvotes", comment.getInt("numUpvotes") + 1);
                            notifyDataSetChanged();
                        } catch (ParseException e) {
                            Log.d(TAG, "upvote not submitted");
                        }
                    }

                }
            };
        }


        /**
         *
         * @param userId
         * @param commentId
         * @return returns true if user has not submitted an upvote for the comment
         * he or she is attempting to upvote
         */
        private boolean hasNotVoted(String userId, String commentId) {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("CommentActivity");
            query.whereEqualTo("commentId", commentId);
            query.whereEqualTo("userId", userId);
            try {
                int voteCount = query.find().size();
                Log.d(TAG, String.format("in has not voted commentId : %s\tuserId : %s\tvoteCount : %d", commentId, userId, voteCount));
                return voteCount == 0 ? true : false;
            }
            catch (ParseException e){
                Log.d(TAG, "bad commentId or userId ");
                return false;
            }
        }



        @Override
        public void notifyDataSetChanged() {
            super.notifyDataSetChanged();
            Collections.sort(mComments, new CommentComparator());
        }
    }

    public class CommentComparator implements Comparator{

        @Override
        public int compare(Object lhs, Object rhs) {
            ParseObject lhsComment = (ParseObject) lhs;
            ParseObject rhsComment = (ParseObject) rhs;
            int lhsUpvotes = lhsComment.getInt("numUpvotes");
            int rhsUpvotes = rhsComment.getInt("numUpvotes");
            Date lhsCreatedAt = lhsComment.getCreatedAt();
            Date rhsCreatedAt = rhsComment.getCreatedAt();

            if( lhsUpvotes != rhsUpvotes){
                return rhsUpvotes - lhsUpvotes;
            }
            else{
                return lhsCreatedAt.after(rhsCreatedAt) ? 1 : -1;
            }
        }
    }
}
