package codezap.template.controller;

import java.net.URI;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import codezap.global.validation.ValidationSequence;
import codezap.member.configuration.BasicAuthentication;
import codezap.member.dto.MemberDto;
import codezap.template.dto.request.CreateTemplateRequest;
import codezap.template.dto.request.UpdateTemplateRequest;
import codezap.template.dto.response.FindAllMyTemplatesResponse;
import codezap.template.dto.response.ExploreTemplatesResponse;
import codezap.template.dto.response.FindAllTemplatesResponse;
import codezap.template.dto.response.FindTemplateResponse;
import codezap.template.service.MyTemplateFacadeService;
import codezap.template.service.TemplateService;

@RestController
@RequestMapping("/templates")
public class TemplateController implements SpringDocTemplateController {

    private final TemplateService templateService;
    private final MyTemplateFacadeService myTemplateFacadeService;

    public TemplateController(TemplateService templateService, MyTemplateFacadeService myTemplateFacadeService) {
        this.templateService = templateService;
        this.myTemplateFacadeService = myTemplateFacadeService;
    }

    @PostMapping
    public ResponseEntity<Void> create(
            @Validated(ValidationSequence.class) @RequestBody CreateTemplateRequest createTemplateRequest,
            @BasicAuthentication MemberDto memberDto
    ) {
        Long createdTemplateId = templateService.createTemplate(createTemplateRequest, memberDto);
        return ResponseEntity.created(URI.create("/templates/" + createdTemplateId))
                .build();
    }

    @GetMapping
    public ResponseEntity<FindAllTemplatesResponse> getTemplates(
            //@RequestParam Long memberId,
            @RequestParam(required = false, defaultValue = "1") Integer pageNumber,
            @RequestParam(required = false, defaultValue = "20") Integer pageSize,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) List<String> tags
    ) {

        return ResponseEntity.ok(templateService.findAllBy(PageRequest.of(pageNumber - 1, pageSize), categoryId, tags));
    }

    @GetMapping("/explore")
    public ResponseEntity<ExploreTemplatesResponse> explore() {
        return ResponseEntity.ok(templateService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FindTemplateResponse> getTemplateById(@PathVariable Long id, @BasicAuthentication MemberDto memberDto) {
        return ResponseEntity.ok(templateService.findByIdAndMember(id, memberDto));
    }

    @GetMapping("/search")
    public ResponseEntity<FindAllMyTemplatesResponse> getMyTemplatesContainTopic(
            @BasicAuthentication MemberDto memberDto,
            @RequestParam("memberId") Long memberId,
            @RequestParam("topic") String topic,
            @PageableDefault Pageable pageable
    ) {
        FindAllMyTemplatesResponse response = myTemplateFacadeService
                .searchMyTemplatesContainTopic(memberDto, memberId, topic, pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity<Void> updateTemplate(
            @PathVariable Long id,
            @Validated(ValidationSequence.class) @RequestBody UpdateTemplateRequest updateTemplateRequest,
            @BasicAuthentication MemberDto memberDto
    ) {
        templateService.update(id, updateTemplateRequest, memberDto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTemplate(@PathVariable Long id, @BasicAuthentication MemberDto memberDto) {
        templateService.deleteById(id, memberDto);
        return ResponseEntity.noContent().build();
    }
}
