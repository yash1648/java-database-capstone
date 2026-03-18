package com.project.back_end;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.project.back_end.repositories.PrescriptionRepository;

@SpringBootTest
class BackEndApplicationTests {

    @MockBean
    private PrescriptionRepository prescriptionRepository;

	@Test
	void contextLoads() {
	}

}
