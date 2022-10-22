package org.jd.demo.lucene;

import java.io.IOException;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.springframework.util.CollectionUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldNameConstants
public class LPeopleDo {

    private Integer id;

    private String name;

    private String desc;

    private String stringValue;

    private List<Integer> luckyNumber;

    /**
     * 保存到lucene
     */
    public void save() {
        Document document = new Document();
        // 可以用来查询，但不保存值
        document.add(new IntPoint(Fields.id, id));
        // 只保存值，不能用来查询，不会分词
        document.add(new StoredField(Fields.id, id));
        // 会索引和分词，是否存储需要指定
        document.add(new TextField(Fields.name, name, Field.Store.YES));
        document.add(new TextField(Fields.desc, desc, Field.Store.YES));
        // 不分词，会索引，是否存储需要指定，同一个字段添加多次存储的值为第一次add，后续add值可作为索引
        document.add(new StringField(Fields.stringValue, stringValue, Field.Store.YES));
        document.add(new StringField(Fields.stringValue, "jjj" + id, Field.Store.YES));

        if (!CollectionUtils.isEmpty(luckyNumber)) {
//            StringBuilder sb = new StringBuilder();
            for (Integer integer : luckyNumber) {
//                sb.append('(').append(integer).append(')');
                document.add(new TextField(Fields.luckyNumber, integer.toString(), Store.YES));
//                document.add(new StoredField(Fields.luckyNumber, integer));
            }
//            document.add(new TextField(Fields.luckyNumber, sb.toString(), Store.YES));
        }
        try {
            IndexWriter indexWriter = LuceneUtils.getIndexWriter();
            indexWriter.addDocument(document);
            indexWriter.commit();
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 复合查询
     */
    public static void findByCompound() {
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        QueryParser queryParser = new QueryParser(Fields.name, new SmartChineseAnalyzer());
        try {
//            builder.add(new BooleanClause(new TermQuery(new Term(Fields.desc, "stringValue3")), BooleanClause.Occur.SHOULD));
//            builder.add(queryParser.parse("张"), BooleanClause.Occur.MUST);
//            builder.add(IntPoint.newRangeQuery(Fields.id, 1, 5), BooleanClause.Occur.MUST);
            builder.add(new BooleanClause(new TermQuery(new Term(Fields.luckyNumber, "7")), BooleanClause.Occur.SHOULD));
            builder.add(new BooleanClause(new TermQuery(new Term(Fields.luckyNumber, "10")), BooleanClause.Occur.SHOULD));
            find(builder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据id范围查询
     */
    public static void findByIdRange(String fieldName, int lower, int upper) {
        System.out.println("------------------范围查询------------------");
        Query query = IntPoint.newRangeQuery(fieldName, lower, upper);
        find(query);
    }

    /**
     * 模糊查询
     * @param fieldName
     * @param keyword
     */
    public static void findByFuzzy(String fieldName, String keyword) {
        System.out.println("------------------模糊查询------------------");
        try {
            // 支持中文分词搜索
            QueryParser queryParser = new QueryParser(fieldName, new SmartChineseAnalyzer());
            find(queryParser.parse(keyword));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 精确查询，不支持中文
     * @param keyword
     */
    public static void findByTerm(String fieldName, String keyword) {
        System.out.println("------------------精确查询------------------");
        // TermQuery 不分词,不支持中文
        find(new TermQuery(new Term(fieldName, keyword)));
    }

    public static void update(String name) {
        Document document = new Document();
        document.add(new TextField(Fields.desc, "update desc", Field.Store.YES));
        // 这里是全部更新，如果不把 name 重新赋值查询不到
        document.add(new TextField(Fields.name, name, Field.Store.YES));
        IndexWriter indexWriter = LuceneUtils.getIndexWriter();
        try {
            indexWriter.updateDocument(new Term(Fields.name, name), document);
            indexWriter.commit();
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void delAll() {
        try {
            IndexWriter indexWriter = LuceneUtils.getIndexWriter();
            indexWriter.deleteAll();
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 基础查询封装
     * @param query
     */
    private static void find(Query query) {
        try {
            IndexReader indexReader = LuceneUtils.getIndexReader();
            IndexSearcher indexSearcher = new IndexSearcher(indexReader);
//            CustomizeCollector<Object> collector = new CustomizeCollector<>(indexSearcher, d -> d);
//            indexSearcher.search(query, collector);
//            System.out.println(collector.getResults());
            print(indexSearcher.search(query, 10), indexSearcher);
            indexReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void print(TopDocs topDocs, IndexSearcher indexSearcher) {
        try {
            System.out.println("命中记录数量：" + topDocs.totalHits);
            for (ScoreDoc doc : topDocs.scoreDocs) {
                Document document = indexSearcher.doc(doc.doc);
                System.out.println(document);
                System.out.println(Fields.id + " : " + document.get(Fields.id));
                System.out.println(Fields.name + " : " + document.get(Fields.name));
                System.out.println(Fields.desc + " : " + document.get(Fields.desc));
                System.out.println(Fields.stringValue + " : " + document.get(Fields.stringValue));
                if (document.getValues(Fields.luckyNumber) != null) {
                    System.out.println(Fields.luckyNumber + " : " + String
                        .join(",", document.getValues(Fields.luckyNumber)));
                }
                System.out.println("-----------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
