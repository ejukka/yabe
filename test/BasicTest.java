import org.junit.*;
import java.util.*;
import play.test.*;
import models.*;

public class BasicTest extends UnitTest {


	@Before
	public void setup(){
		Fixtures.deleteAll();
	}

    @Test
    public void aVeryImportantThingToTest() {
        assertEquals(2, 1 + 1);
    }

    @Test
    public void createAndRetrieveUser() {
    	new User("test@test.com", "123", "test").save();

    	User testUser = User.find("byEmail", "test@test.com").first();

    	assertNotNull(testUser);
    	assertEquals("test", testUser.fullname);
    }

    @Test
    public void createAndRetrieveUserWithTwoUsers() {
    	new User("test@test.com", "123", "test").save();
    	new User("test@test.com", "1234", "test2").save();

    	User testUser = User.find("byEmail", "test@test.com").first();

    	assertNotNull(testUser);
    	assertFalse("test2".equalsIgnoreCase(testUser.fullname));
    	assertEquals("test", testUser.fullname);
    }

    @Test
	public void tryConnectAsUser() {
	    
	    new User("bob@gmail.com", "secret", "Bob").save();
	     
	    assertNotNull(User.connect("bob@gmail.com", "secret"));
	    assertNull(User.connect("bob@gmail.com", "badpassword"));
	    assertNull(User.connect("tom@gmail.com", "secret"));
	}

	@Test
	public void createPost() {
	    // Create a new user and save it
	    User bob = new User("bob@gmail.com", "secret", "Bob").save();
	    
	    // Create a new post
	    new Post(bob, "My first post", "Hello world").save();
	    
	    // Test that the post has been created
	    assertEquals(1, Post.count());
	    
	    // Retrieve all posts created by Bob
	    List<Post> bobPosts = Post.find("byAuthor", bob).fetch();
	    
	    // Tests
	    assertEquals(1, bobPosts.size());
	    Post firstPost = bobPosts.get(0);
	    assertNotNull(firstPost);
	    assertEquals(bob, firstPost.author);
	    assertEquals("My first post", firstPost.title);
	    assertEquals("Hello world", firstPost.content);
	    assertNotNull(firstPost.postedAt);
	}

}
