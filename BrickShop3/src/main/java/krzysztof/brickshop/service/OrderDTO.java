package krzysztof.brickshop.service;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@EqualsAndHashCode
public class OrderDTO {
    private final String orderReferenceId;
    private final int bricksCount;
}