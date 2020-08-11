package com.example.demoelasticserch.controller;

import com.alibaba.fastjson.JSON;
import com.example.demoelasticserch.bean.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class EsController {

    @Autowired
    private RestHighLevelClient restHighLevelClient;
    public static final String INDEX_NAME = "zhishiku";

    /**
     * 创建索引
     * @return
     * @throws IOException
     */
    @RequestMapping("/createIndex")
    public CreateIndexResponse createIndex() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("zhishiku");
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        return createIndexResponse;
    }

    /**
     * 删除索引
     */
    @RequestMapping("/dropIndex")
    public AcknowledgedResponse dropIndex() throws IOException {
        DeleteIndexRequest deleteIndexRequest = new DeleteIndexRequest("zgc");
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(deleteIndexRequest, RequestOptions.DEFAULT);
        return delete;
    }

    /**
     * 判断索引是否存在
     */
    @RequestMapping("/existIndex")
    public boolean existIndex() throws IOException {
        GetIndexRequest getIndexRequest = new GetIndexRequest(INDEX_NAME);
        boolean exists = restHighLevelClient.indices().exists(getIndexRequest, RequestOptions.DEFAULT);
        return exists;

    }

    /**
     * 添加文档
     * @throws Exception
     */
    @RequestMapping("/addDocument")
    public void addDocument() throws Exception {
        User user = new User("xiali", 19);
        IndexRequest indexRequest = new IndexRequest(INDEX_NAME);
        indexRequest.source(JSON.toJSONString(user), XContentType.JSON);
        indexRequest.timeout(TimeValue.timeValueSeconds(1));
        indexRequest.id();

        IndexResponse index = restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        System.out.println(index);

    }

    /**
     * 判断文档是否存在
     */
    @RequestMapping("/existDocument")
    public boolean existDocument() throws IOException {
        GetRequest request = new GetRequest(INDEX_NAME);
        request.id("2");
        boolean exists = restHighLevelClient.exists(request, RequestOptions.DEFAULT);
        return exists;
    }

    /**
     * 更新文档
     */
    @RequestMapping("/updateDocument")
    public UpdateResponse updateDocument () throws IOException {
        UpdateRequest request = new UpdateRequest(INDEX_NAME,"2");
        User llala = new User("llala", 23);
        request.doc(JSON.toJSONString(llala),XContentType.JSON);
        request.timeout("1s");
        UpdateResponse update = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        return update;
    }

    /**
     * 获取文档
     *
     */
    @RequestMapping("/getDocument")
    public GetResponse getDocument () throws IOException {
        GetRequest request = new GetRequest(INDEX_NAME);
        request.id("2");
        GetResponse documentFields = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        Map<String, DocumentField> fields =  documentFields.getFields();
        String sourceAsString = documentFields.getSourceAsString();
        Map<String, Object> map = documentFields.getSourceAsMap();
        System.out.println(fields);
        System.out.println(sourceAsString);
        System.out.println(map);

        return documentFields;
    }

    /**
     * 删除文档
     */
    @RequestMapping("/deleteDocument")
    public DeleteResponse deleteDocument() throws IOException {
        DeleteRequest request = new DeleteRequest(INDEX_NAME,"2");
        DeleteResponse delete = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        return delete;
    }



}
