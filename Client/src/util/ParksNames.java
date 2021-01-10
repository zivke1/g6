package util;
/**
 * Enum to maintain uniformity in park names
 */
public enum ParksNames {
	Tal_Park {
		@Override
		public String toString() {
			return "Tal Park";
		}
	},
	Carmel_Park {
		@Override
		public String toString() {
			return "Carmel Park";
		}
	},
	Jordan_Park {
		@Override
		public String toString() {
			return "Jordan Park";
		}
	};
}
