package eu.senla.converter;

import eu.senla.dto.EntityDTO;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Log4j2
public class Mapper {
    private ModelMapper modelMapper;

    public Object convertToEntity(Object object, EntityDTO dto) {
        log.info("Attempt to convert to entity");
        return modelMapper.map(dto, object.getClass());
    }

    public EntityDTO convertToDto(Object object, EntityDTO dto) {
        log.info("Attempt to convert to dto");
        return modelMapper.map(object, dto.getClass());
    }

}
