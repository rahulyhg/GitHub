package edge.app.modules.tagCreation;

public interface TagCreationsService {

	TagCreation saveTagCreation(TagCreation tagCreation) throws Exception;

	TagCreation getTagCreation(int tagCreationId);

	TagCreation getTagCreation(String addressEmail);

}
