package edu.rpi.rocs;

import java.util.ArrayList;

import org.w3c.dom.Node;

/**
 * Represents a complex type described through a WSDL description file.
 * 
 * @author ewpatton
 *
 */
public class WSDLComplexType {
	/**
	 * Used to represent a complex type that is described as part of another
	 * complex type.
	 * 
	 * @author ewpatton
	 *
	 */
	protected class WSDLComplexTypeComponent {
		protected String name;
		protected int type;
		protected WSDLComplexType complexType;

		/**
		 * Default constructor
		 *
		 */
		public WSDLComplexTypeComponent() {
			name = "";
			type = 0;
			complexType = null;
		}
		
		/**
		 * Takes a slot name and type for that slot.
		 * 
		 * @param aName The name of the slot
		 * @param aType The type of the slot
		 * 
		 * @see WSDLTypes
		 */
		public WSDLComplexTypeComponent(String aName, int aType) {
			name = aName;
			type = aType;
			complexType = null;
		}
		
		/**
		 * Takes a DOM node structure and transforms it into a
		 * WSDLComplexType.
		 * 
		 * @param aDesc
		 */
		public WSDLComplexTypeComponent(Node aDesc) {
			
		}
		
		/**
		 * Takes a slot name and another WSDLComplexTypeComponent in order to
		 * build a tree of nested complex types.
		 * 
		 * @param aName
		 * @param aType
		 */
		public WSDLComplexTypeComponent(String aName, WSDLComplexTypeComponent aType) {
			name = aName;
			type = WSDLTypes.xsdAnyComplexType;
		}
	}
	
	protected ArrayList components=new ArrayList();
	
	/**
	 * Creates a new complex type.
	 *
	 */
	public WSDLComplexType() {
		
	}
	
	/**
	 * Creates a new complex type given a DOM node.
	 * @param aDesc
	 */
	public WSDLComplexType(Node aDesc) {
	
	}
	
	/**
	 * Returns the number of slots in this complex type
	 */
	public int componentCount() {
		return components.size();
	}
}
