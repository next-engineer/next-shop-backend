package com.next.app.api.product.dto;

import com.next.app.api.product.entity.Product;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Schema(name = "ProductSummary", description = "검색 결과용 상품 요약")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductSummaryDto {

    @Schema(description = "상품 ID", example = "101")
    private Long id;

    @Schema(description = "상품명", example = "iPhone 15 Pro")
    private String name;

    @Schema(description = "가격", example = "1590000")
    private BigDecimal price;

    public static ProductSummaryDto from(Product p) {
        return new ProductSummaryDto(p.getId(), p.getName(), p.getPrice());
    }
}
