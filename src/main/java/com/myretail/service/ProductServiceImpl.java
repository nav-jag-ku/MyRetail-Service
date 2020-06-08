package com.myretail.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myretail.exception.MyRetailException;
import com.myretail.model.CurrentPrice;
import com.myretail.model.Product;
import com.myretail.repository.ProductRepository;
import com.myretail.vo.CurrentPriceVo;
import com.myretail.vo.ProductName;
import com.myretail.vo.ProductVo;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService{

    Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${product-rest-endpoint}")
    private String apiEndpointURL;

    @Override
    public List<Product> fetchAllProducts() {
        return null;
    }

    @Override
    public Product insertProduct(Product product) {
        System.out.println("=="+product.getProductId());
        return productRepository.save(product);
    }

    @Override
    public ProductVo fetchProductDetailsById(int prodId) {
        Product product = productRepository.findById(prodId).get();
        System.out.println(":::"+product.getProductId());
        ProductVo prodResponse = null;
        prodResponse = generateProductResponse(product, null);
        return prodResponse;
    }

    /**
     * Fetching the data by ID and consuming the data from eternal API
     * and preparting the response with product name
     * @param prodId
     * @return
     */
    @Override
    public ProductVo fetchDataForID(int prodId) throws JSONException {
        logger.debug("Entering :: Class - ProductServiceImpl :: Method - fetchDataForID()");
        String productName = null;
        ProductVo prodResponse = null;
        Product product = productRepository.findById(prodId).get();
        if(product != null) {
            //Consuming the product name from external API
            productName = getProductNameByRemoteCall(prodId);
            prodResponse = generateProductResponse(product, productName);
        }else{
            logger.debug("Product Not Found :: Having Exception while Fetching product data from DB ");
        }
        return prodResponse;
    }

    /**
     * Updating the data by Id
     * @param productVo
     * @return
     */
    @Override
    public boolean updateProductById(ProductVo productVo) throws MyRetailException {
        logger.debug("Entering :: Class - ProductServiceImpl :: Method - updateProductById()");
        boolean result = false;
        try {
            Product product = productRepository.findById(productVo.getProductId()).get();
            if(product != null){
                Product prod = getProductInstance(productVo);
                productRepository.save(prod);
                result = true;
            }else{
                logger.debug("Product Not Found :: Having Exception while Fetching product data from DB ");
            }
        } catch (Exception ex) {
            logger.error("Product Not Found in database " + ex);
            throw new MyRetailException(HttpStatus.NOT_FOUND.value(),"Product not found in DB");
        }
        return result;
    }


    private Product getProductInstance(ProductVo productVo) {
        Product product = new Product();
        CurrentPrice currentPrice = new CurrentPrice();
        product.setProductId(productVo.getProductId());
        currentPrice.setCurrencyCode(productVo.getCurrentPrice().getCurrencyCode());
        currentPrice.setValue(productVo.getCurrentPrice().getValue());
        product.setCurrentPrice(currentPrice);
        return product;
    }

    /**
     * Preparing the response.
     * @param product
     * @param productName
     * @return
     */
    private ProductVo generateProductResponse(Product product, String productName) {
        logger.debug("Entering :: Class - ProductServiceImpl :: Method - generateProductResponse()");
        System.out.println("---------------");
        ProductVo prodResponse = new ProductVo();
        CurrentPriceVo currentPriceResponse= new CurrentPriceVo();
        prodResponse.setProductId(product.getProductId());
        try{

            currentPriceResponse.setCurrencyCode(product.getCurrentPrice().getCurrencyCode());
            currentPriceResponse.setValue(product.getCurrentPrice().getValue());
            prodResponse.setCurrentPrice(currentPriceResponse);
            prodResponse.setName(productName);
        }
        catch(Exception e) {
            logger.error("Exception for setting the current price values "+ e.getCause());
            e.printStackTrace();
        }
        return prodResponse;
    }

    /**
     * calling an external api to consume the product name
     * @param prodId
     * @return
     */
    private String getProductNameByRemoteCall(int prodId) throws JSONException {

        logger.debug("Entering :: Class - ProductServiceImpl :: Method - getProductNameByRemoteCall()");
        String prodUri="products/v3/";
        String productName= "The Big Lebowski (Blu-ray) (Widescreen)";
        try {
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiEndpointURL+prodUri+prodId).queryParam("excludes","fields=descriptions&id_type=TCI N&key=43cJWpLjH8Z8oR18KdrZDBKAgLLQKJjz");
            //TO-DO we can use "exchange" method if we have request headers are available.
            ProductName jsonResponse = restTemplate.getForObject(builder.build().encode().toUri(),ProductName.class);
            //TO-Do Assuming received the response
            if(jsonResponse != null){
                productName = jsonResponse.getProdName();
            }
            else{
                logger.debug("Product Name is not availbale in the response.");
            }
        }catch (RestClientException e) {
            logger.error("Rest end point is unavailable  :" + apiEndpointURL+ prodUri+ prodId);
            logger.error("Not having any response- ", e.getCause());
        }
        //TO-DO as of now rest is failing so setting default name .
        finally{
            return productName;
        }
    }


    /**
     * Preparing the payload
     * @param prodId
     * @return
     * @throws JSONException
     */

    private JSONObject prepareRequest(int prodId) throws JSONException {
        JSONObject json = new JSONObject();
        json.put("id", prodId);
        logger.debug("request payload");
        return json;
    }
}
