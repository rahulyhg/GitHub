package org.hibernate.criterion;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomExample extends Example{

	private static final long serialVersionUID = 1L;
	private boolean isLikeEnabled;
	private boolean isIgnoreCaseEnabled;
	private Character escapeCharacter;
	private MatchMode matchMode;
	private boolean isInEnabled = false;

	public CustomExample(Object entity) {
		super(entity, new NotNullPropertySelector());		
	}

	/**
	 * Use the "like" operator for all string-valued properties
	 */
	public Example enableLike(MatchMode matchMode) {
		
		super.enableLike(matchMode);
		
		isLikeEnabled = true;
		this.matchMode = matchMode;
		return this;
	}

	/**
	 * Use the "like" operator for all string-valued properties
	 */
	public Example enableLike() {		
		super.enableLike();
		return enableLike(MatchMode.EXACT);
	}
	
	/**
	 * Use the "like" operator for all string-valued properties
	 */
	public Example enableIn() {		
		isInEnabled = true;
		return this;
	}

	/**
	 * Ignore case for all string-valued properties
	 */
	public Example ignoreCase() {		
		super.ignoreCase();
		isIgnoreCaseEnabled = true;
		return this;
	}
	
	/**
	 * Set escape character for "like" clause
	 */
	public Example setEscapeCharacter(Character escapeCharacter) {
		
		super.setEscapeCharacter(escapeCharacter);
		this.escapeCharacter = escapeCharacter;
		return this;
	}
	
	@Autowired
	protected void appendPropertyCondition(
			String propertyName,
			Object propertyValue,
			Criteria criteria,
			CriteriaQuery cq,
			StringBuffer buf)
		throws HibernateException {
			Criterion crit;
			String critCondition = null;
			if ( propertyValue!=null ) {
				boolean isString = propertyValue instanceof String;
				if(isString && isInEnabled && ((String) propertyValue).contains(",")){					
					critCondition = getCriteriaCondition(propertyName, ((String) propertyValue).split(","));					
				} else if ( isLikeEnabled && isString ) {
						crit = new LikeExpression(
								propertyName,
								( String ) propertyValue,
								matchMode,
								escapeCharacter,
								isIgnoreCaseEnabled
						);
						critCondition = crit.toSqlString(criteria, cq);
					}
					else {
						crit = new SimpleExpression( propertyName, propertyValue, "=", isIgnoreCaseEnabled && isString );
						critCondition = crit.toSqlString(criteria, cq);
					}
			}
			else {
				crit = new NullExpression(propertyName);
				critCondition = crit.toSqlString(criteria, cq);
			}
			
			if ( buf.length()>1 && critCondition.trim().length()>0 ) buf.append(" and ");
			buf.append(critCondition);
		}

	private String getCriteriaCondition(String propertyName, String[] split) {
		StringBuffer str = new StringBuffer();
		str.append(" ( " + propertyName + " in ( ");
		
		int length = split.length;
		
		for(int i = 0 ; i < length - 1 ; i++){
			str.append( "'" +	split[i] + "',"	);	
		}
		
		str.append( "'" +	split[length - 1] + "'"	);			
		str.append(" ) " + " or " + propertyName + " = ? )");
		return str.toString();
	}

}