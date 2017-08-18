package edge.app.modules.tagCreation;

public interface TagCreationsService {

	TagCreation saveTagCreation(TagCreation tagCreation) throws Exception;

	TagCreation getTagCreation(Long tagCreationId);

	TagCreation getTagCreationByEmail(String addressEmail);

}
