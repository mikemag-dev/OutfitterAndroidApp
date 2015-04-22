package com.outfitterandroid;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
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

        mCommentsListView.setAdapter(new CommentsAdapter(mSubmissionId));
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
                    ParseObject comment = new ParseObject("Comment");
                    comment.put("comment", commentText);
                    comment.put("numUpvotes", 0);
                    comment.put("submissionId", mSubmissionId);
                    comment.put("userId", ParseUser.getCurrentUser().getObjectId());
                    try{
                        comment.save();
                        //refresh comments list
                        mCommentsListView.setAdapter(new CommentsAdapter(mSubmissionId));
                    }
                    catch(ParseException e){
                        Log.d(TAG, "bad comment save");
                    }

                }
            }
        });
    }

    public class CommentsAdapter extends BaseAdapter {
        private static final String TAG = "CommentsAdapter";

        String mSubmissionId;
        List<ParseObject> mComments;


        public CommentsAdapter(String submissionId) {
            mSubmissionId = submissionId;
        }

        @Override
        public int getCount() {
            int count = 0;
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Comment");
            query.whereEqualTo("submissionId", mSubmissionId);
            try{
                mComments = query.find();
                Collections.sort(mComments, new CommentComparator());
                count = mComments.size();
            }
            catch (ParseException e){
                Log.d(TAG, "no comments found");
            }

            return count;
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
            ParseObject comment = (ParseObject) getItem(position);
            String commentText = comment.getString("comment");
            String numUpvotes = Integer.toString(comment.getInt("numUpvotes"));
            String userId, username;
            userId = username = comment.getString("userId");
            SimpleDateFormat dt = new SimpleDateFormat("hh:mm MM-dd-yyyy");
            String createdAt = dt.format(comment.getCreatedAt());
            try {
                ParseQuery<ParseObject> query = ParseQuery.getQuery("User");
                username = query.get(userId).getString("username");
            }
            catch (ParseException e){
                Log.d(TAG, "bad user objectId");
            }

            //fill views
            TextView numUpvotesTextView = (TextView) convertView.findViewById(R.id.num_upvotes_text_view);
            TextView commenterCommentTextView = (TextView) convertView.findViewById(R.id.commenter_comment_text_view);
            TextView commenterUsernameTextView = (TextView) convertView.findViewById(R.id.commenter_username_text_view);
            TextView commenterCommentCreatedAtTextView = (TextView) convertView.findViewById(R.id.commenter_created_at_text_view);

            numUpvotesTextView.setText(numUpvotes);
            commenterCommentTextView.setText(commentText);
            commenterUsernameTextView.setText(username);
            commenterCommentCreatedAtTextView.setText(createdAt);

            convertView.setClickable(false);
            return convertView;
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
                return rhsUpvotes = lhsUpvotes;
            }
            else{
                return lhsCreatedAt.after(rhsCreatedAt) ? 1 : -1;
            }
        }
    }
}
