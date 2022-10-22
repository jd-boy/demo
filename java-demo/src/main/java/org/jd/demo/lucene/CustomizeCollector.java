package org.jd.demo.lucene;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.search.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.function.Function;

/**
 * 用于获取全部搜索结果
 * @param <E>
 */
public class CustomizeCollector<E> implements Collector {

    private Collection<E> results;

    private final IndexSearcher indexSearcher;

    private final Function<Document, E> function;

    public CustomizeCollector(IndexSearcher indexSearcher, Function<Document, E> function) {
        this(indexSearcher, function, null);
    }

    public CustomizeCollector(IndexSearcher indexSearcher, Function<Document, E> function, Collection<E> results) {
        if (indexSearcher == null) {
            throw new NullPointerException("IndexSearcher must not be null");
        }
        if (indexSearcher == null) {
            throw new NullPointerException("Function must not be null");
        }
        this.indexSearcher = indexSearcher;
        this.function = function;
        this.results = results == null ? new ArrayList() : results;
    }

    @Override
    public LeafCollector getLeafCollector(LeafReaderContext context) throws IOException {
        return new LeafCollector() {
            @Override
            public void setScorer(Scorable scorer) throws IOException {

            }

            @Override
            public void collect(int doc) throws IOException {
                results.add(function.apply(indexSearcher.doc(doc)));
            }
        };
    }

    @Override
    public ScoreMode scoreMode() {
        return ScoreMode.COMPLETE;
    }

    /**
     * @return 搜索结果
     */
    public Collection<E> getResults() {
        return this.results;
    }

}
