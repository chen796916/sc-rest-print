package com.chen.utils;

import com.itextpdf.text.Font;
import com.itextpdf.text.pdf.BaseFont;
import org.springframework.util.ResourceUtils;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public final class HtmlUtil {

    private HtmlUtil() {
    }

    private static final String FONT_PATH = "classpath:simsun.ttc";

    public static void html2Pdf(String html, String pdfFile) {
        String pdfDir = StringUtils.substringBeforeLast(pdfFile, "/");
        File file = new File(pdfDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        try (OutputStream os = new FileOutputStream(pdfFile)) {
            ITextRenderer renderer = new ITextRenderer();
            renderer.setDocumentFromString(html);
            ITextFontResolver fontResolver = renderer.getFontResolver();
            String fontPath = ResourceUtils.getFile(FONT_PATH).getAbsolutePath();
            fontResolver.addFont(fontPath, BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            renderer.layout();
            renderer.createPDF(os);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
