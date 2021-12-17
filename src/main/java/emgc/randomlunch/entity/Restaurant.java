package emgc.randomlunch.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import emgc.randomlunch.enums.RestaurantStatus;
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

	private String lotNumberAddress;

	private String streetNameAddress;

	@Enumerated(EnumType.STRING)
	private RestaurantStatus status;

	private Double x;

	private Double y;

	private BigDecimal latitude;

	private BigDecimal longitude;

}
