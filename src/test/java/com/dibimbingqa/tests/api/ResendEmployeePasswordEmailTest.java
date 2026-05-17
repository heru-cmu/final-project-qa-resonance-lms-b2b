package com.dibimbingqa.tests.api;

import com.dibimbingqa.models.responses.ResendEmployeePasswordEmailResponse;
import com.dibimbingqa.services.ResendEmployeePasswordEmailService;
import com.dibimbingqa.utils.ApiResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ResendEmployeePasswordEmailTest extends BaseAuthenticatedTest {

    @Test
    public void TC_API_EMP_MUT_006_ResendEmailValid() {
        ApiResponse<ResendEmployeePasswordEmailResponse> response =
                ResendEmployeePasswordEmailService.resendEmployeePasswordEmail(
                        "acc62af9-aa13-46b5-a7fa-7191cc2a1398"
                );

        Assert.assertNotNull(response.getResponseBody().data.resendEmployeePasswordEmail.id,
                "Email terkirim ulang");
    }

    @Test
    public void TC_API_EMP_MUT_006_NEG_ResendEmailInvalid() {
        ApiResponse<ResendEmployeePasswordEmailResponse> response =
                ResendEmployeePasswordEmailService.resendEmployeePasswordEmail("");

        Assert.assertNull(response.getResponseBody().data.resendEmployeePasswordEmail,
                "Error / tidak ada request");
    }
}
