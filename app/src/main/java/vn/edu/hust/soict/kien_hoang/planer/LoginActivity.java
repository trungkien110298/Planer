package vn.edu.hust.soict.kien_hoang.planer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Mã để xác minh quyền truy cập đọc dữ liệu
     */
    private static final int REQUEST_READ_CONTACTS = 0;


    private Cursor user = null;
    /**
     * Theo dõi cấp quyền sau đăng nhập để có thể hủy bỏ quyền nếu được yêu cầu
     */
    private UserLoginTask mAuthTask = null;

    // Các đối tượng tương ứng với trên giao diện
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private UserHelper userHelper;

    // Ghi đè hàm khởi tạo của Activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Khởi tạo đối tượng userHelper để làm việc với cơ sở dữ liệu bảng users
        userHelper = new UserHelper(this);

        setContentView(R.layout.activity_login);

        // Thiết lập mẫu đăng nhập
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        // Cập nhật tự động hoàn thành email đăng nhập nếu có
        populateAutoComplete();
        // Lấy thông tin đăng nhập - mật khẩu để xác thực
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        // Lấy thông tin và xác thực email đăng nhập
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        TextView mEmailSignUpButton = (TextView) findViewById(R.id.email_sign_up_button);
        mEmailSignUpButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });

        // Lấy đối tượng từ View
        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
    }

    // Ghi đè hàm onDestroy: đóng cơ sở dữ liệu sau khi xử lý hoàn tất
    @Override
    public void onDestroy() {
        super.onDestroy();
        userHelper.close();
    }

    // Xác thực tài khoản sau khi ấn đăng nhập
    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }
        getLoaderManager().initLoader(0, null, this);
    }

    // Xác thực tài khoản đăng nhập
    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        // Kiểm tra quyền người dùng
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        // Kiểm tra các yêu cầu được gửi tới về cấp quyền
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new View.OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    /**
     * Nhận lại callback khi yêu cầu cấp quyền được hoàn tất
     */
    // Quyền đăng nhập được xác thực
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Đăng nhập hoặc đăng ký bằng một tài khoản xác đinh riêng biệt
     * Nếu có lỗi xảy ra thì lỗi sẽ được hiển thị trên giao diện
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // đặt lại lỗi
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Lưu trữ giá trị số lần đăng nhập không thành công
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Kiểm tra xác thực mật khẩu sau khi người dùng nhập vào
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Xác thực tài khoản do người dùng nhập vào
        if (TextUtils.isEmpty(email)) { // Nếu người dùng không điền vào trường tài khoản
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) { // Nếu mẫu email không hợp lệ
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }


        if (cancel) {
            // Nếu có lỗi xảy ra, màn hình sẽ tập trung vào lỗi
            focusView.requestFocus();
        } else {
            // Kiểm tra email và mật khẩu
            // Hiển thị thanh tiến trình xử lý xác minh tài khoản và mật khẩu
            // Hiển thị số lần đăng nhập không thành công liên tiếp
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }
    // Hàm xác minh email hợp lệ
    private boolean isEmailValid(String email) {
        return email.contains("@");
    }
    // Hàm xác minh mật khẩu hợp lệ
    private boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    /**
     * Hiển thị tiến trình đăng nhập và ẩn mẫu đăng nhập
     */

    // Hiển thị tiến trình
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // Xử lý giao diện hình ảnh cho phần tiến trình
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);
            // Ẩn tiến trình
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });
            // Hiển thị tiến trình
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // Nếu không có API của những thiết bị không có API thì ẩn giao diện xử lí tiến trình
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    // Ghi đè phương thức truyền dữ liệu để kiểm tra thông tin
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Truyền bản ghi dữ liệu tới thông tin của thiết bị của người dùng
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Chọn chỉ nhận địa chỉ email
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Hiển thị email chính, nếu không thì hiển thị những email người dùng sử dụng nhiều nhất
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    //Tải danh sách email đã nhập của người dùng
    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Tạo Adapter để hiển thị danh sách các email người dùng đã nhập theo thứ tự tần suất giảm dần
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Hiển thị công việc đồng bộ cho Đăng nhập và đăng kí sau khi xác thực tài khoản
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Integer> {

        private final String mEmail;
        private final String mPassword;
        // Gán lại tài khoản người dùng đã đăng nhập lại
        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Integer doInBackground(Void... params) {
            user = userHelper.getByUsername(mEmail);
            startManagingCursor(user);


            if (user.getCount() == 0)
                return 1;

            user.moveToFirst();
            if (userHelper.getPassword(user).equals(mPassword))
                return 0;

            return 2;
        }

        // Thực hiện ngầm tiến trình xác thực tài khoản
        @Override
        protected void onPostExecute(final Integer success) {
            mAuthTask = null;
            showProgress(false);

            if (success == 0) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            } else if (success == 1) {
                mEmailView.setError(getString(R.string.error_invalid_email));
                mEmailView.requestFocus();
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        // Đăng nhập thất bại, hủy bỏ tiến trình
        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

