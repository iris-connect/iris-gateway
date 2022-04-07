package iris.backend_service.locations.search.db.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

@Entity
@Indexed
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Location {

	@EmbeddedId
	private LocationIdentifier id;

	@FullTextField(name = "name_search", analyzer = "german")
	@GenericField(sortable = Sortable.YES)
	private String name;

	@FullTextField(name = "officialName_search", analyzer = "german")
	@GenericField(sortable = Sortable.YES)
	private String contactOfficialName;

	@FullTextField(name = "representative_search", analyzer = "german")
	@GenericField(sortable = Sortable.YES)
	private String contactRepresentative;

	@FullTextField(name = "street_search", analyzer = "german")
	@GenericField(sortable = Sortable.YES)
	private String contactAddressStreet;

	@KeywordField(sortable = Sortable.YES, normalizer = "german")
	private String contactAddressCity;

	@GenericField(sortable = Sortable.YES)
	private String contactAddressZip;

	private String contactOwnerEmail;

	@KeywordField(sortable = Sortable.YES, normalizer = "german")
	private String contactEmail;

	@GenericField(sortable = Sortable.YES)
	private String contactPhone;
}
