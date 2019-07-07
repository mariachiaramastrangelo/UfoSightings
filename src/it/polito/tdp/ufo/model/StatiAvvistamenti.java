package it.polito.tdp.ufo.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StatiAvvistamenti {
	private String stato;
	private List<LocalDateTime> dateAvvistamenti = new ArrayList<LocalDateTime>();
	
	
	public StatiAvvistamenti(String stato) {
		super();
		this.stato = stato;
	}
	public String getStato() {
		return stato;
	}
	public void setStato(String stato) {
		this.stato = stato;
	}
	public List<LocalDateTime > getDateAvvistamenti() {
		return dateAvvistamenti;
	}
	public void setDateAvvistamenti(List<LocalDateTime > dateAvvistamenti) {
		this.dateAvvistamenti = dateAvvistamenti;
	}
	
	public void add(LocalDateTime data) {
		dateAvvistamenti.add(data);
	}
	@Override
	public String toString() {
		return stato;
	}
	public boolean haUnEventoPrecedente( StatiAvvistamenti s2) {
		for(LocalDateTime d1: this.dateAvvistamenti) {
			for(LocalDateTime d2: s2.dateAvvistamenti) {
				if (d2.isAfter(d1)) {
					return true;
				}
			}
		}
		return false;
		
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stato == null) ? 0 : stato.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StatiAvvistamenti other = (StatiAvvistamenti) obj;
		if (stato == null) {
			if (other.stato != null)
				return false;
		} else if (!stato.equals(other.stato))
			return false;
		return true;
	}
	
	
}
