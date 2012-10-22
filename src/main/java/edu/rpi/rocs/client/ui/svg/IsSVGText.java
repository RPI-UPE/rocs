package edu.rpi.rocs.client.ui.svg;

public interface IsSVGText {
	public enum TextAnchor {
		Start {
			public String toString() { return "start"; }
		},
		Middle {
			public String toString() { return "middle"; }
		},
		End {
			public String toString() { return "end"; }
		},
		Inherit {
			public String toString() { return "inherit"; }
		}
	};
	public void setTextAnchor(TextAnchor a);
	public TextAnchor getTextAnchor();
	public void setText(String text);
	public String getText();
	public void setFontFamily(String font);
	public String getFontFamily();
	public void setFontSize(String size);
	public String getFontSize();
}
