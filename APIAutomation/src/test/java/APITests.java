import io.restassured.http.ContentType;
import org.junit.Test;
import pojos.ResourcePojo;

import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;


public class APITests {

    @Test
    public void getResourceById2(){
        given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .basePath("/posts/5")
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(200)
                .and().body("id", is (5))
                .and().body(matchesJsonSchemaInClasspath("posts-schema.json"));
    }

    @Test
    public void filteringByParameters2(){
        List<ResourcePojo> resourceList = given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .contentType(ContentType.JSON)
                .when().get("/posts?userId=3")
                .then()
                .statusCode(200)
                .and().body(matchesJsonSchemaInClasspath("list-schema.json"))
                .extract().jsonPath().getList("", ResourcePojo.class);
        assertThat(resourceList).extracting(ResourcePojo::getUserId).isNotNull();
        assertThat(resourceList).extracting(ResourcePojo::getUserId).hasSize(10);
        assertThat(resourceList).extracting(ResourcePojo::getUserId).containsOnly(3);
    }


    @Test
    public void getListResources2(){
        List<ResourcePojo> resourceList = given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .basePath("/posts")
                .contentType(ContentType.JSON)
                .when().get()
                .then().statusCode(200)
                .and().body(matchesJsonSchemaInClasspath("list-schema.json"))
                .extract().jsonPath().getList("", ResourcePojo.class);
        assertThat(resourceList).extracting(ResourcePojo::getId).isNotNull();
        assertThat(resourceList).extracting(ResourcePojo::getId).hasSize(100);
        assertThat(resourceList).extracting(ResourcePojo::getId).contains(1, 50, 100);
        assertThat(resourceList).extracting(ResourcePojo::getUserId).contains(1, 5, 10);
    }
}
