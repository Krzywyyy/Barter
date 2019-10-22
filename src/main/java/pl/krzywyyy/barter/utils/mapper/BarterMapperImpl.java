package pl.krzywyyy.barter.utils.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;

@Component
public class BarterMapperImpl implements BarterMapper {
    private final ModelMapper modelMapper;

    @Autowired
    public BarterMapperImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public <V> V map(Object object, Type clazz) {
        return modelMapper.map(object, clazz);
    }
}
