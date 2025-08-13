package com.example.next.app.api.product.controller;

import com.example.next.app.api.product.dto.ProductSummaryDto;
import com.example.next.app.api.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Products", description = "상품 API")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;
    public ProductController(ProductService productService) { this.productService = productService; }

    @Operation(
            summary = "상품명 검색",
            description = """
            q(상품명)만으로 검색합니다. 결과는 최대 50건, id 내림차순으로 반환합니다.
            - 예) /api/products?q=iphone
            - q가 비어있으면 [] 반환
            """
    )
    @ApiResponse(
            responseCode = "200",
            description = "성공",
            content = @Content(
                    array = @ArraySchema(schema = @Schema(implementation = ProductSummaryDto.class)),
                    examples = @ExampleObject(value = "[{\"id\":101,\"name\":\"iPhone 15 Pro\",\"price\":1590000}]")
            )
    )
    @GetMapping
    public ResponseEntity<List<ProductSummaryDto>> search(
            @RequestParam(name = "q", required = false, defaultValue = "") String q
    ) {
        return ResponseEntity.ok(productService.searchByNameSimple(q));
    }

    @Operation(summary = "상품 단건 조회", description = "ID로 상품을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "성공",
            content = @Content(schema = @Schema(implementation = ProductSummaryDto.class)))
    @ApiResponse(responseCode = "404", description = "존재하지 않는 상품")
    @GetMapping("/{id}")
    public ResponseEntity<ProductSummaryDto> get(@PathVariable Long id) {
        var dto = productService.getById(id);
        return (dto == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(dto);
    }
}
