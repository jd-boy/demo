package org.jd.demo.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.IOException;
import java.nio.file.Paths;

public class LuceneUtils {

    /**
     * 支持中文分词
     */
    private static final Analyzer chineseAnalyzer = new SmartChineseAnalyzer();

    private static final Analyzer standardAnalyzer = new StandardAnalyzer();

    public static Directory directory;

    static {
        try {
            directory = FSDirectory.open(Paths.get("/Users/jd/data/lucene"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static IndexReader getIndexReader() {
        try {
            return DirectoryReader.open(directory);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static IndexWriter getIndexWriter() {
        try {
            chineseAnalyzer.setVersion(Version.LUCENE_8_11_0);
            IndexWriterConfig indexWriterConfig = new IndexWriterConfig(chineseAnalyzer);
            return new IndexWriter(directory, indexWriterConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
