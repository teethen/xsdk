package com.teethen.xsdk;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.provider.OpenableColumns;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;
import com.teethen.sdk.base.XConstant;
import com.teethen.sdk.xutil.FileUtil;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 如果引用，会造成安装包大13MB左右，根据项目实际情况使用。
 */
public class PdfViewActivity extends BaseActivity implements OnPageChangeListener, OnLoadCompleteListener {

    private static final String TAG = PdfViewActivity.class.getSimpleName();

    //public static final String PDF_SRC_TYPE = "";
    public static final String PDF_SRC_FILEPATH = "pdf_src_filepath";
    public static final String PDF_SRC_FILENAME = "pdf_src_filename";

    private final static int REQUEST_CODE = 42;
    private static final int PERMISSION_CODE = 42042;
    private static final String URI_SUFFIX = "://";
    public static final String MIME_PDF_EXT = ".pdf";
    public static final String SAMPLE_FILE = "sample.pdf";
    public static final String READ_EXTERNAL_STORAGE = "android.permission.READ_EXTERNAL_STORAGE";

    private Toolbar toolbar;
    private PDFView pdfView;
    private Uri uri;
    private String pdfFilePath;
    private String pdfFileName;
    private Integer pageNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdfview);

        try {
            toolbar = findViewById(R.id.toolbar);
            initToolBar(toolbar, getString(R.string.act_pdf_view));
            initView();
        } catch (Exception e) {
        }
    }

    private void initView() {
        pdfView = (PDFView) findViewById(R.id.pdfView);
        pdfFilePath = getIntent().getStringExtra(PDF_SRC_FILEPATH);
        pdfFileName = getIntent().getStringExtra(PDF_SRC_FILENAME);
        if (!TextUtils.isEmpty(pdfFilePath)) {
            uri = Uri.parse(XConstant.SCHEME_FILE + URI_SUFFIX + pdfFilePath);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.pdfview_options, menu);
        return true;
    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        if (menu != null) {
            if (menu.getClass() == MenuBuilder.class) {
                try {
                    Method m = menu.getClass().getDeclaredMethod("setOptionalIconsVisible", Boolean.TYPE);
                    m.setAccessible(true);
                    m.invoke(menu, true);
                } catch (Exception e) {
                }
            }
        }
        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.pdfPickFile) {
            pickFile();
        }
        return super.onOptionsItemSelected(item);
    }

    void pickFile() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, READ_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{READ_EXTERNAL_STORAGE}, PERMISSION_CODE);
            return;
        }
        launchPicker();
    }

    private void launchPicker() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(XConstant.MIME_PDF);
        try {
            startActivityForResult(intent, REQUEST_CODE);
        } catch (ActivityNotFoundException e) {
            showToast(getString(R.string.pdf_pick_file_error)); //alert user that file manager not working
        }
    }

    void afterViews() {
        pdfView.setBackgroundColor(Color.LTGRAY);

        if (!TextUtils.isEmpty(pdfFilePath)) {
            if (!pdfFilePath.endsWith(MIME_PDF_EXT)) {
                String ext = FileUtil.getExtension(pdfFilePath);
                showToast(String.format("无法打开 %s 格式的文件", ext));
                return;
            }
        }

        if (uri != null) {
            displayFromUri(uri);
        } else {
            displayFromAsset(SAMPLE_FILE);
        }
        setTitle(pdfFileName);
    }

    @Override
    protected void onStart() {
        super.onStart();
        afterViews();
    }

    private void displayFromAsset(String assetFileName) {
        pdfFileName = assetFileName;
        pdfView.fromAsset(SAMPLE_FILE)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) //in dp
                .load();
    }

    private void displayFromUri(Uri uri) {
        if (TextUtils.isEmpty(pdfFileName)) {
            pdfFileName = getFileName(uri);
        }

        pdfView.fromUri(uri)
                .defaultPage(pageNumber)
                .onPageChange(this)
                .enableAnnotationRendering(true)
                .onLoad(this)
                .scrollHandle(new DefaultScrollHandle(this))
                .spacing(10) // in dp
                .load();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                uri = data.getData();
                displayFromUri(uri);
            }
        }
    }

    @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber = page;
        toolbar.setTitle(String.format("%s %s / %s", pdfFileName, page + 1, pageCount));
    }

    public String getFileName(Uri uri) {
        String result = null;
        if (uri.getScheme().equals(XConstant.SCHEME_CONTENT)) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            try {
                if (cursor != null && cursor.moveToFirst()) {
                    result = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                }
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    @Override
    public void loadComplete(int nbPages) {
        /*PdfDocument.Meta meta = pdfView.getDocumentMeta();
        Log.i(TAG, "title = " + meta.getTitle());
        Log.i(TAG, "author = " + meta.getAuthor());
        Log.i(TAG, "subject = " + meta.getSubject());
        Log.i(TAG, "keywords = " + meta.getKeywords());
        Log.i(TAG, "creator = " + meta.getCreator());
        Log.i(TAG, "producer = " + meta.getProducer());
        Log.i(TAG, "creationDate = " + meta.getCreationDate());
        Log.i(TAG, "modDate = " + meta.getModDate());
        printBookmarksTree(pdfView.getTableOfContents(), "-");*/
    }

    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {
            Log.i(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));
            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

    /**
     * Listener for response to user permission request
     *
     * @param requestCode  Check that permission request code matches
     * @param permissions  Permissions that requested
     * @param grantResults Whether permissions granted
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchPicker();
            }
        }
    }

}
