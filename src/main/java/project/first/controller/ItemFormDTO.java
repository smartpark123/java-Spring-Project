package project.first.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter @Setter
public class ItemFormDTO {

    private Long id;

    @NotEmpty(message = "물품명을 입력해주세요")
    private String itemName;

    @NotEmpty(message = "가격을 입력해주세요")
    private int price;

    @NotEmpty(message = "판매하실 수량을 입력해주세요")
    private int stockQuantity;

    @NotEmpty(message = "전화번호를 입력해주세요")
    private String sellerHP;

    private String category;

    @NotEmpty(message = "제목을 입력해주세요")
    private String title;

    @NotEmpty(message = "내용을 입력해주세요")
    private String sellerComment;

    private MultipartFile imageFiles;


}
