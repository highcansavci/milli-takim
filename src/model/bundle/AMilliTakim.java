package model.bundle;

import java.util.Arrays;
import java.util.Comparator;

public class AMilliTakim {
	
	private Futbolcu[] futbolcular;
	private int currentSize = 0;
	private int maxSize;
	private boolean isDuplicateAllowed = true;
	private final boolean IS_REVERSED = true;
	
	public AMilliTakim(int maxSize) {
		this.maxSize = maxSize;
		futbolcular = new Futbolcu[this.maxSize];
	}
	
	public int getCurrentSize() {
		return currentSize;
	}
	
	public void setDuplicateAllowed(boolean isDuplicateAllowed) {
		this.isDuplicateAllowed = isDuplicateAllowed;
	}
	
	public boolean getDuplicateAllowed() {
		return isDuplicateAllowed;
	}
	
	public Futbolcu find(Futbolcu futbolcu) {
		for(int i = 0; i < currentSize; ++i) {
			if(futbolcular[i].equals(futbolcu)) {
				futbolcular[i].changeFoundStatus(Found.FOUND);
				return futbolcular[i];
			}
		}
		return null;
	}
	
	private int findIndex(Futbolcu futbolcu, int offset) {
		for(int i = offset + 1; i < currentSize; ++i) {
			if(futbolcular[i].equals(futbolcu))
				return i;
		}
		return -1;
	}
	
	private int findRating(int rating, int offset, boolean reversed) {
		int low = offset;
		int high = currentSize - 1;
		while(low < high) {
			int mid = (low + high) / 2;
			if(futbolcular[mid].getRating() == rating)
				return mid;
			else if(futbolcular[mid].getRating() < rating) {
				low = reversed ? low : mid + 1;
				high = reversed ? mid - 1 : high;
			}
			else {
				low = reversed ? mid + 1 : low;
				high = reversed ? high : mid - 1;
			}
		}
		return -1;
	}
	
	public void insert(int formaNo, String firstName, String lastName) {
		// Ayný forma numarasýndan iki tane olamaz.
		for(int i = 0; i < currentSize; ++i)
			if(futbolcular[i].getFormaNo() == formaNo && !isDuplicateAllowed)
				return;
		Futbolcu newFutbolcu = new Futbolcu(formaNo, firstName, lastName);
		if(currentSize < maxSize && (!isDuplicateAllowed && find(newFutbolcu) == null || isDuplicateAllowed)) {
			int idx;
			for(idx = 0; idx < currentSize; ++idx) 
				if(futbolcular[idx].getRating() > newFutbolcu.getRating())
					break;
			for(int i = currentSize; i > idx; --i) 
				futbolcular[i] = futbolcular[i - 1];
			futbolcular[idx] = newFutbolcu;
			currentSize++;
		}
	}
	
	private void swap(int firstPos, int secondPos) {
		Futbolcu temp = futbolcular[firstPos];
		futbolcular[firstPos] = futbolcular[secondPos];
		futbolcular[secondPos] = temp;
	}
	
	
	public void sort(int low, int high, Attributes attribute, SortKey key) {
		if (low < high)
	    {
	        int idx = partition(low, high, attribute, key);
	        sort(low, idx, attribute, key);
	        sort(idx + 1, high, attribute, key);
	    }
	}
	
	public int partition(int low, int high, Attributes attribute, SortKey key) {
		int i = low - 1;
		int j = high + 1;
		int pivot;
		String pivotStr;
		
		switch (attribute) {
		case FORMA_NO:
			pivot = futbolcular[low].getFormaNo();
			if(key == SortKey.ASCENDING) {	
				while(true) {
					do {
						--j;
					} while(futbolcular[j].getFormaNo() > pivot);
					do {
						++i;
					} while(futbolcular[i].getFormaNo() < pivot);
					if(i >= j) {
						return j;
					}
					swap(i, j);
				}
			}
			else {
				while(true) {
					do {
						--j;
					} while(futbolcular[j].getFormaNo() < pivot);
					do {
						++i;
					} while(futbolcular[i].getFormaNo() > pivot);
					if(i >= j) {
						return j;
					}
					swap(i, j);
				}
			}
			
		case FIRST_NAME:
			pivotStr = futbolcular[low].getFirstName();
			if(key == SortKey.ASCENDING) {	
				while(true) {
					do {
						--j;
					} while(futbolcular[j].getFirstName().compareTo(pivotStr) > 0);
					do {
						++i;
					} while(futbolcular[i].getFirstName().compareTo(pivotStr) < 0);
					if(i >= j) {
						return j;
					}
					swap(i, j);
				}
			}
			else {
				while(true) {
					do {
						--j;
					} while(futbolcular[j].getFirstName().compareTo(pivotStr) < 0);
					do {
						++i;
					} while(futbolcular[i].getFirstName().compareTo(pivotStr) > 0);
					if(i >= j) {
						return j;
					}
					swap(i, j);
				}
			}
			
		case LAST_NAME:
			pivotStr = futbolcular[low].getLastName();
			if(key == SortKey.ASCENDING) {	
				while(true) {
					do {
						--j;
					} while(futbolcular[j].getLastName().compareTo(pivotStr) > 0);
					do {
						++i;
					} while(futbolcular[i].getLastName().compareTo(pivotStr) < 0);
					if(i >= j) {
						return j;
					}
					swap(i, j);
				}
			}
			else {
				while(true) {
					do {
						--j;
					} while(futbolcular[j].getLastName().compareTo(pivotStr) < 0);
					do {
						++i;
					} while(futbolcular[i].getLastName().compareTo(pivotStr) > 0);
					if(i >= j) {
						return j;
					}
					swap(i, j);
				}
			}

		case RATING:
			pivot = futbolcular[low].getRating();
			if(key == SortKey.ASCENDING) {	
				while(true) {
					do {
						--j;
					} while(futbolcular[j].getRating() > pivot);
					do {
						++i;
					} while(futbolcular[i].getRating() < pivot);
					if(i >= j) {
						return j;
					}
					swap(i, j);
				}
			}
			else {
				while(true) {
					do {
						--j;
					} while(futbolcular[j].getRating() < pivot);
					do {
						++i;
					} while(futbolcular[i].getRating() > pivot);
					if(i >= j) {
						return j;
					}
					swap(i, j);
				}
			}
			
		default:
			return -1;
		}
	}
	
	public void delete(Futbolcu futbolcu) {
		int index = findIndex(futbolcu, -1);
		while(index != -1) {
			for(int i = index; i < currentSize - 1; ++i) 
				futbolcular[i] = futbolcular[i+1];
			currentSize--;
			index = findIndex(futbolcu, index - 1);
		}
	}
	
	public void removeMaxRating() {
		int maxRating = getMaxRating();
		int index = findRating(maxRating, 0, IS_REVERSED);
		while(index != -1) {
			for(int i = index; i < currentSize - 1; ++i) 
				futbolcular[i] = futbolcular[i+1];
			currentSize--;
			index = findRating(maxRating, 0, IS_REVERSED);
		}
	}
	
	public void fillTakim() {
		if(maxSize >= 26) {
			futbolcular[0] = new Futbolcu(23, "Ugurcan", "Cakir");
			futbolcular[1] = new Futbolcu(12, "Altay", "Bayindir");
			futbolcular[2] = new Futbolcu(1, "Mert", "Gunok");
			futbolcular[3] = new Futbolcu(4, "Caglar", "Soyuncu");
			futbolcular[4] = new Futbolcu(3, "Merih", "Demiral");
			futbolcular[5] = new Futbolcu(15, "Ozan", "Kabak");
			futbolcular[6] = new Futbolcu(22, "Kaan", "Ayhan");
			futbolcular[7] = new Futbolcu(13, "Umut", "Meras");
			futbolcular[8] = new Futbolcu(18, "Rýdvan", "Yilmaz");
			futbolcular[9] = new Futbolcu(2, "Zeki", "Celik");
			futbolcular[10] = new Futbolcu(25, "Mert", "Muldur");
			futbolcular[11] = new Futbolcu(5, "Okay", "Yokuslu");
			futbolcular[12] = new Futbolcu(14, "Talyan", "Antalyali");
			futbolcular[13] = new Futbolcu(19, "Orkun", "Kokcu");
			futbolcular[14] = new Futbolcu(6, "Ozan", "Tufan");
			futbolcular[15] = new Futbolcu(21, "Irfan Can", "Kahveci");
			futbolcular[16] = new Futbolcu(8, "Dorukhan", "Tokoz");
			futbolcular[17] = new Futbolcu(10, "Hakan", "Calhanoglu");
			futbolcular[18] = new Futbolcu(20, "Abdulkadir", "Omur");
			futbolcular[19] = new Futbolcu(24, "Kerem", "Akturkoglu");
			futbolcular[20] = new Futbolcu(7, "Cengiz", "Under");
			futbolcular[21] = new Futbolcu(11, "Yusuf", "Yazici");
			futbolcular[22] = new Futbolcu(16, "Enes", "Unal");
			futbolcular[23] = new Futbolcu(26, "Halil", "Dervisoglu");
			futbolcular[24] = new Futbolcu(17, "Burak", "Yilmaz");
			futbolcular[25] = new Futbolcu(9, "Kenan", "Karaman");
		}
		currentSize = 26;
		Arrays.sort(futbolcular, (Futbolcu f1, Futbolcu f2) -> reverseSignum() * Integer.signum(f1.getRating() - f2.getRating()));
	}
	
	private int reverseSignum() {
		return IS_REVERSED ? -1 : 1;
	}
	
	public Futbolcu get(int index) {
		if(index < currentSize)
			return futbolcular[index];
		return null;
	}
	
	public void display() {
		for(int i = 0; i < currentSize; ++i) {
			futbolcular[i].toString();
			System.out.println();
		}
	}
	
	public int getMaxRating() {
		int maxRating = -1;
		if(futbolcular != null || currentSize > 0) {
			for(int i = 0; i < currentSize; ++i) {
				if(futbolcular[i].getRating() > maxRating)
					maxRating = futbolcular[i].getRating();
			}
		}
		return maxRating;
	}
}
