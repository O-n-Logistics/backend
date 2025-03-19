package on.logistics.productservice.application.service;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import on.logistics.productservice.application.dto.CreateProductRequestDto;
import on.logistics.productservice.application.dto.SearchProductRequestDto;
import on.logistics.productservice.application.dto.UpdateIncreaseProductQuantityRequestDto;
import on.logistics.productservice.application.dto.UpdateProductRequestDto;
import on.logistics.productservice.application.dto.UpdateReduceProductQuantityRequestDto;
import on.logistics.productservice.domain.Product;
import on.logistics.productservice.domain.dto.CreateProductDto;
import on.logistics.productservice.domain.dto.UpdateProductDto;
import on.logistics.productservice.domain.repository.ProductRepository;
import on.logistics.productservice.exception.ProductException;
import on.logistics.productservice.exception.ProductExceptionCode;
import on.logistics.productservice.global.application.dtos.PageDto;
import on.logistics.productservice.presentation.dtos.response.CreateProductResponse;
import on.logistics.productservice.presentation.dtos.response.GetProductResponse;
import on.logistics.productservice.presentation.dtos.response.SearchProductResponse;
import on.logistics.productservice.presentation.dtos.response.UpdateIncreaseProductQuantityResponse;
import on.logistics.productservice.presentation.dtos.response.UpdateProductResponse;
import on.logistics.productservice.presentation.dtos.response.UpdateReduceProductQuantityResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    @Transactional
    public CreateProductResponse createProduct(CreateProductRequestDto requestDto) {

        // todo : 유저의 아이디 정보를 받아와서 권한 체크 필요
        CreateProductDto createProductDto = CreateProductDto.from(requestDto);

        Product product = Product.create(createProductDto);
        Product saved = productRepository.save(product);
        return CreateProductResponse.of(saved.getId());
    }

    @Override
    public PageDto<SearchProductResponse> searchProduct(SearchProductRequestDto requestDto) {
        Page<Product> products = productRepository.searchProduct(requestDto);
        Page<SearchProductResponse> responsePage = products.map(SearchProductResponse::from);
        return PageDto.from(responsePage);
    }

    @Override
    public GetProductResponse getProduct(UUID id) {
        Product product = getOrElseThrow(id);
        return GetProductResponse.from(product);
    }

    @Override
    @Transactional
    public UpdateProductResponse updateProduct(UpdateProductRequestDto requestDto) {

        // todo : 유저의 아이디 정보를 받아와서 권한 체크 필요
        UpdateProductDto updateProductDto = UpdateProductDto.from(requestDto);
        Product product = getOrElseThrow(updateProductDto.productId());
        product.update(updateProductDto);
        return UpdateProductResponse.of(product.getId());
    }

    @Override
    @Transactional
    public void deleteProduct(UUID id) {
        // todo : 유저의 아이디 정보를 받아와서 권한 체크 필요
        Product product = getOrElseThrow(id);
        productRepository.delete(product);
    }

    @Override
    @Transactional
    public UpdateReduceProductQuantityResponse updateReduceProductQuantity(
        UpdateReduceProductQuantityRequestDto requestDto) {
        Product product = getOrElseThrow(requestDto.productId());

        if (product.getQuantity().getValue() == 0) {
            throw new ProductException(ProductExceptionCode.PRODUCT_QUANTITY_LIMIT);
        }

        product.updateReduceQuantity(requestDto.productQuantity());
        return UpdateReduceProductQuantityResponse.of(product.getId());
    }

    @Override
    @Transactional
    public UpdateIncreaseProductQuantityResponse updateIncreaseProductQuantity(
        UpdateIncreaseProductQuantityRequestDto requestDto) {
        Product product = getOrElseThrow(requestDto.productId());

        product.updateIncreaseQuantity(requestDto.productQuantity());
        return UpdateIncreaseProductQuantityResponse.of(product.getId());
    }

    private Product getOrElseThrow(UUID id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new ProductException(
                ProductExceptionCode.PRODUCT_IS_NOT_FOUND));
    }
}