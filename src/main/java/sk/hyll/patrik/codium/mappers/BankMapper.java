package sk.hyll.patrik.codium.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import sk.hyll.patrik.codium.model.CardOwner;
import sk.hyll.patrik.codium.model.dto.BankDTO;

/**
 * Mapper
 * Converts response body(JSON) into DTO object and vice versa
 * @link https://mapstruct.org/documentation/dev/reference/html/
 */
@Mapper
public interface BankMapper {
    // Recommended in the documentation
    BankMapper INSTANCE = Mappers.getMapper( BankMapper.class );

    // When a property has a different name in the target entity, its name can be specified via the @Mapping annotation.
    // @Mapping(target = "name", source = "name_s")
    BankDTO bankCardToBankCardDTO(CardOwner cardOwner);
    CardOwner bankCardDTOtoBankCard(BankDTO bankCardDTO);
}
