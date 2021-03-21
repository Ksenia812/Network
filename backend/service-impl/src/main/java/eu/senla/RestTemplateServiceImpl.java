package eu.senla;

import eu.senla.dto.CommunityDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Log4j2
@Component
public class RestTemplateServiceImpl {
    @Autowired
    private RestTemplate restTemplate;
    private  static  final String REQUEST_URI = "http://localhost:8081";

    public CommunityDTO getCommunityByName(String name) {
        Map<String,String> authorization = new HashMap<>();
        authorization.put("Authorization","eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNYXggU3Zpcmlkb3YiLCJpYXQiOjE2MTYzNTIxNDUsImV" +
                "4cCI6MTYxNjM3MDE0NX0.zwJHL-9Wo4RQn_7WUNw--eorPt8RqTSvzSusVWxbeeuBnv0sqWD5dG-yjKBx--06XsX53edpEnHBUp_UEhY4WQ");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        httpHeaders.set("Authorization","Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJNYXggU3Zpcmlkb3YiLCJpYXQiOjE2MTYzNTIxNDUsImV" +
                "4cCI6MTYxNjM3MDE0NX0.zwJHL-9Wo4RQn_7WUNw--eorPt8RqTSvzSusVWxbeeuBnv0sqWD5dG-yjKBx--06XsX53edpEnHBUp_UEhY4WQ");
        HttpEntity<?> request = new HttpEntity(httpHeaders);
        ResponseEntity<CommunityDTO> response = restTemplate.exchange(REQUEST_URI+ "/community/restTemplate/name?name=" + name, HttpMethod.GET,request,CommunityDTO.class);

    /* CommunityDTO  communityDTO= restTemplate.getForObject(REQUEST_URI + "/restTemplate/name?name"+name,CommunityDTO.class);*/
     return response.getBody();
    }
}
