package emgc.randomlunch.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant extends BaseEntity {

	@Id
	@GeneratedValue
	@Column(name = "restaurant_id")
	private Long id;

	@OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
	private List<Thumbnail> thumbnailList = new ArrayList<>();

	private String name;
	private String address;
	private String latitude;
	private String longitude;

}
