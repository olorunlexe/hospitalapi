package com.assessment.hospitalapi.filter;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class RequestWrapper extends HttpServletRequestWrapper {
    ByteArrayInputStream bais;

    ByteArrayOutputStream baos;

    BufferedServletInputStream bsis;

    byte[] buffer;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        InputStream is = request.getInputStream();
        baos = new ByteArrayOutputStream();
        byte buf[] = new byte[1024];
        int letti;
        while ((letti = is.read(buf)) > 0) {
            baos.write(buf, 0, letti);
        }
        buffer = baos.toByteArray();
    }


    public ServletInputStream getInputStream() {
        try {
            bais = new ByteArrayInputStream(buffer);
            bsis = new BufferedServletInputStream(bais);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return bsis;
    }

    public byte[] toByteArray() {
        return Arrays.copyOf(buffer, buffer.length);
    }


    private static class BufferedServletInputStream extends ServletInputStream {

        ByteArrayInputStream bais;

        public BufferedServletInputStream(ByteArrayInputStream bais) {
            this.bais = bais;
        }

        public int available() {
            return bais.available();
        }

        public int read() {
            return bais.read();
        }

        public int read(byte[] buf, int off, int len) {
            return bais.read(buf, off, len);
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }
    }
}