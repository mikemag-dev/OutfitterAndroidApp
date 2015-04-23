package com.outfitterandroid;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
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
 */
public class CommentsActivity extends Activity{
    public static final String EXTRA_SUBMISSION_ID = "com.outfitterandroid.submissionId";
    private static final String TAG = "CommentsActivity";

    final String COMMENT_EMPTY = "comment text is empty";

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
        try {
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Comment");
            query.whereEqualTo("submissionId", mSubmissionId);
            mComments = query.find();
            for(ParseObject comment : mComments){
                comment.put("numUpvotes", getNumUpvotes(comment.getObjectId())) ;
            }
            Collections.sort(mComments, new CommentComparator());
            mCommentsListView.setAdapter(new CommentsAdapter(mSubmissionId, mComments));
        }
        catch(ParseException e){
            Log.d(TAG, "bad comment save");
        }
        mAddCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String commentText = mCommentEditText.getText().toString();
                if (commentText.isEmpty()) {
                    Toast.makeText(CommentsActivity.this, COMMENT_EMPTY, Toast.LENGTH_SHORT).show();
                }
                else if (false/* content filter */){

                }
                else{
                    mCommentEditText.setText("");
                    View focusedView = CommentsActivity.this.getCurrentFocus();
                    if (null != focusedView){
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromInputMethod(focusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                    ParseObject comment = new ParseObject("Comment");
                    comment.put("comment", commentText);
                    //comment.put("numUpvotes", 0);
                    comment.put("submissionId", mSubmissionId);
                    comment.put("userId", ParseUser.getCurrentUser().getObjectId());
                    try{
                        comment.save();
                        //comment.saveInBackground();
                        //refresh comments list
                        ParseQuery<ParseObject> query = ParseQuery.getQuery("Comment");
                        query.whereEqualTo("submissionId", mSubmissionId);
                        mComments = query.find();
                        Collections.sort(mComments, new CommentComparator());
                        CommentsAdapter ca = (CommentsAdapter) mCommentsListView.getAdapter();
                        comment.put("numUpvotes", 0);
                        ca.addComment(comment);
                        ca.notifyDataSetChanged();
                    }
                    catch(ParseException e){
                        Log.d(TAG, "bad comment save");
                    }

                }
            }
        });
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
        List<ParseObject> mComments;


        public CommentsAdapter(String submissionId, List<ParseObject> comments) {
            mSubmissionId = submissionId;
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
        public View getView(int position, View convertView, ViewGroup parent) {
            if (null == convertView){
                convertView = getLayoutInflater().inflate(R.layout.comment_element, null);
            }

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
            upvoteLinearLayout.setOnClickListener(new View.OnClickListener() {
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
            });

            convertView.setClickable(false);
            return convertView;
        }



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

        public void addComment(ParseObject comment) {
            mComments.add(comment);
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
