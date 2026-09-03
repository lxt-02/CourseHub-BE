package course.courseservice;

import course.courseservice.application.port.CategoryEventPublisherPort;
import course.courseservice.application.port.CourseEventPublisherPort;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class CourseServiceApplicationTests {

    @MockitoBean
    private CourseEventPublisherPort courseEventPublisherPort;

    @MockitoBean
    private CategoryEventPublisherPort categoryEventPublisherPort;

    @Test
    void contextLoads() {
    }

}
