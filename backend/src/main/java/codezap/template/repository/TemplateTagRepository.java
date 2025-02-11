package codezap.template.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import codezap.template.domain.Tag;
import codezap.template.domain.Template;
import codezap.template.domain.TemplateTag;

public interface TemplateTagRepository extends JpaRepository<TemplateTag, Long> {

    List<TemplateTag> findAllByTemplate(Template template);

    void deleteAllByTemplateId(Long id);

    List<TemplateTag> findByTagIn(List<Tag> tags);
}
