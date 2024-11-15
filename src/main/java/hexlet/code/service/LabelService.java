package hexlet.code.service;

import hexlet.code.dto.label.LabelCreateDTO;
import hexlet.code.dto.label.LabelDTO;
import hexlet.code.dto.label.LabelUpdateDTO;
import hexlet.code.exception.ResourceNotFoundException;
import hexlet.code.mapper.LabelMapper;
import hexlet.code.model.Label;
import hexlet.code.repository.LabelRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LabelService {
    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private LabelMapper labelMapper;


    public List<LabelDTO> getAll() {
        List<Label> labels = labelRepository.findAll();

        List<LabelDTO> dto = labels.stream()
                .map(labelMapper::map)
                .toList();
        return dto;
    }

    public LabelDTO findById(Long id) {
        Label label = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Label with id=" + id + " not found!"));

        LabelDTO dto = labelMapper.map(label);
        return dto;
    }

    public LabelDTO create(LabelCreateDTO createDTO) {
        Label label = labelMapper.map(createDTO);
        labelRepository.save(label);

        LabelDTO dto = labelMapper.map(label);
        return dto;
    }

    public LabelDTO update(LabelUpdateDTO data, Long id) {
        Label label = labelRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Label with id=" + id + " not found!"));
        labelMapper.update(data, label);
        labelRepository.save(label);

        LabelDTO dto = labelMapper.map(label);
        return dto;
    }

    public void delete(Long id) {
        labelRepository.deleteById(id);
    }
}
