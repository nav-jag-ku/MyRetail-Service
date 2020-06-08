package com.myretail.rest;

import com.myretail.exception.MyRetailException;
import com.myretail.model.Product;
import com.myretail.repository.ProductRepository;
import com.myretail.service.ProductService;
import com.myretail.vo.ProductResponse;
import com.myretail.vo.ProductVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @Autowired
    ProductService productService;

    Logger logger = LoggerFactory.getLogger(ProductController.class);

    /**
     * Method is for insert the data
     * @param product
     * @return
     */
    @PostMapping(path="/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveProduct(@RequestBody Product product){

        logger.debug("Entering :: Class - ProductController :: Method - saveProduct()");
        ResponseEntity<?> response = null;
        try{
            System.out.println("---"+product.getProductId());
            response = ResponseEntity.status(HttpStatus.OK).body(productService.insertProduct(product));
            logger.debug("successfully load the product data : ");
        }catch(Exception ex){
            logger.error("ProductController - Error" + ex.getMessage() + "Error Cause" + ex.getCause());
            return getResponse(logger, ex);
        }
        logger.debug("Completed :: Class - ProductController :: Method - saveProduct()");
        return response;
    }

    /**
     * Fetching all the product details from the db
     * @return
     */
    @GetMapping(path="/products",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProdutDetails(){

        logger.debug("Entering :: Class - ProductController :: Method - getProdutDetails()");
        ResponseEntity<?> response = null;
        Product product = null;
        try{
            response = ResponseEntity.status(HttpStatus.OK).body(repository.findAll());
        }catch(Exception ex){
            logger.error("ProductController - Error" + ex.getMessage() + "Error Cause" + ex.getCause());
            return getResponse(logger, ex);
        }
        logger.debug("Completed :: Class - ProductController :: Method - getProdutDetails()");
        return response;
    }

    /**
     * Fetching data by Id with price details.
     * @param prodId
     * @return
     */

    @GetMapping(path="/products/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getProductByID(@PathVariable("id") int prodId){

        logger.debug("Entering :: Class - ProductController :: Method - getProductByID(), prodId : " + prodId);
        ResponseEntity<?> response = null;
        Product product = null;
        try{
            response = ResponseEntity.status(HttpStatus.OK).body(productService.fetchDataForID(prodId));
        }catch(Exception ex){
            logger.error("ProductController - Error" + ex.getMessage() + "Error Cause" + ex.getCause());
            return getResponse(logger, ex);
        }
        logger.debug("Completed :: Class - ProductController :: Method - getProductByID()");
        return response;
    }

    /**
     * Updating price data by Id.
     * @param product
     * @param prodId
     * @return
     */

    @PutMapping(value = "/products/{id}", produces =MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePriceDetails(@RequestBody ProductVo product, @PathVariable("id") int prodId) throws MyRetailException {
        logger.debug("Entering :: Class - ProductController :: Method - updatePriceDetails(), prodId : " + prodId);
        ResponseEntity<?> response = null;
        boolean result = false;
        try{
            result = productService.updateProductById(product);
            if (result) {
                response = ResponseEntity.status(200).body(result);
            } else {
                response = ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("Product Not Found in the DB");
            }
        } catch (Exception exc) {
            logger.error("Error while Updating product "+exc);
            if(exc.getMessage().equalsIgnoreCase("Product not found in DB")){
                response =  ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Not Found in the DB");
                return response;
            }
            throw new MyRetailException(HttpStatus.NOT_FOUND.value(),"Product not found while update");
        }
        return response;
    }


    /**
     *
     * @param logger
     * @param e
     * @return
     */
    public ResponseEntity<?> getResponse(Logger logger, Exception e) {

        ProductResponse errormessage = new ProductResponse();
        errormessage.copy(logger, e);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.APPLICATION_JSON).body(errormessage);
    }
}