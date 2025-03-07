/*
 * Copyright Terracotta, Inc.
 * Copyright IBM Corp. 2024, 2025
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.terracotta.toolkit.search.attribute;

import org.terracotta.toolkit.search.SearchException;

import java.util.HashMap;
import java.util.Map;

public enum ToolkitAttributeType {

  /**
   * Boolean type
   */
  BOOLEAN {
    /**
     * {@inheritDoc}
     */
    @Override
    public void validateValue(String name, Object value) {
      if (!(value instanceof Boolean)) { throw new SearchException("Expecting a Boolean value for attribute [" + name
                                                                   + "] but was " + type(value)); }
    }
  },

  /**
   * Byte type
   */
  BYTE {
    /**
     * {@inheritDoc}
     */
    @Override
    public void validateValue(String name, Object value) {
      if (!(value instanceof Byte)) { throw new SearchException("Expecting a Byte value for attribute [" + name
                                                                + "] but was " + type(value)); }
    }
  },

  /**
   * Character type
   */
  CHAR {
    /**
     * {@inheritDoc}
     */
    @Override
    public void validateValue(String name, Object value) {
      if (!(value instanceof Character)) { throw new SearchException("Expecting a Character value for attribute ["
                                                                     + name + "] but was " + type(value)); }
    }
  },

  /**
   * Double type
   */
  DOUBLE {
    /**
     * {@inheritDoc}
     */
    @Override
    public void validateValue(String name, Object value) {
      if (!(value instanceof Double)) { throw new SearchException("Expecting a Double value for attribute [" + name
                                                                  + "] but was " + type(value)); }
    }
  },

  /**
   * Float type
   */
  FLOAT {
    /**
     * {@inheritDoc}
     */
    @Override
    public void validateValue(String name, Object value) {
      if (!(value instanceof Float)) { throw new SearchException("Expecting a Float value for attribute [" + name
                                                                 + "] but was " + type(value)); }
    }
  },

  /**
   * Integer type
   */
  INT {
    /**
     * {@inheritDoc}
     */
    @Override
    public void validateValue(String name, Object value) {
      if (!(value instanceof Integer)) { throw new SearchException("Expecting an Integer value for attribute [" + name
                                                                   + "] but was " + type(value)); }
    }
  },

  /**
   * Long type
   */
  LONG {
    /**
     * {@inheritDoc}
     */
    @Override
    public void validateValue(String name, Object value) {
      if (!(value instanceof Long)) { throw new SearchException("Expecting a Long value for attribute [" + name
                                                                + "] but was " + type(value)); }
    }
  },

  /**
   * Short type
   */
  SHORT {
    /**
     * {@inheritDoc}
     */
    @Override
    public void validateValue(String name, Object value) {
      if (!(value instanceof Short)) { throw new SearchException("Expecting a Short value for attribute [" + name
                                                                 + "] but was " + type(value)); }
    }
  },

  /**
   * Date type
   */
  DATE {
    /**
     * {@inheritDoc}
     */
    @Override
    public void validateValue(String name, Object value) {
      if (value == null || value.getClass() != java.util.Date.class) { throw new SearchException(
                                                                                                 "Expecting a java.util.Date value for attribute ["
                                                                                                     + name
                                                                                                     + "] but was "
                                                                                                     + type(value)); }
    }
  },

  /**
   * SQL Date type
   */
  SQL_DATE {
    /**
     * {@inheritDoc}
     */
    @Override
    public void validateValue(String name, Object value) {
      if (value == null || value.getClass() != java.sql.Date.class) { throw new SearchException(
                                                                                                "Expecting a java.sql.Date value for attribute ["
                                                                                                    + name
                                                                                                    + "] but was "
                                                                                                    + type(value)); }
    }
  },

  /**
   * Enum type
   */
  ENUM {
    /**
     * {@inheritDoc}
     */
    @Override
    public void validateValue(String name, Object value) {
      if (!(value instanceof Enum)) { throw new SearchException("Expecting a enum value for attribute [" + name
                                                                + "] but was " + type(value)); }
    }
  },

  /**
   * String type
   */
  STRING {
    /**
     * {@inheritDoc}
     */
    @Override
    public void validateValue(String name, Object value) {
      if (!(value instanceof String)) { throw new SearchException("Expecting a String value for attribute [" + name
                                                                  + "] but was " + type(value)); }
    }
  };

  private static final Map<Class, ToolkitAttributeType> MAPPINGS = new HashMap<Class, ToolkitAttributeType>();

  /**
   * Get the appropriate {@link ToolkitAttributeType} enum for the given object value.
   * 
   * @param name the attribute name (only meaningful to message if exception thrown)
   * @param value the value to lookup the type for
   * @return the attribute type for this value
   * @throws SearchException if the given value is not valid for a search attribute
   */
  public static ToolkitAttributeType typeFor(String name, Object value) throws SearchException {
    if (name == null) { throw new NullPointerException("null name"); }
    if (value == null) { throw new NullPointerException("null value"); }

    ToolkitAttributeType type = typeForOrNull(value instanceof Enum ? ((Enum) value).getDeclaringClass() : value
        .getClass());
    if (type != null) { return type; }

    throw new SearchException("Unsupported type for search attribute [" + name + "]: " + value.getClass().getName());
  }

  public static ToolkitAttributeType typeFor(Class<?> c) {
    if (c == null) throw new NullPointerException("null class");

    return typeForOrNull(c);
  }

  /**
   * Test the given value to see if it is a legal type
   * 
   * @param value
   * @return true if the given value is valid as a search attribute
   */
  public static boolean isSupportedType(Object value) {
    if (value == null) { return true; }

    return typeForOrNull(value.getClass()) != null;
  }

  /**
   * Validate that the given value is in fact of the correct type
   * 
   * @param name the attribute name (only meaningful to message if exception thrown)
   * @param value the value to validate against this type
   * @throws SearchException if the given value is not a valid instance of this type
   */
  public abstract void validateValue(String name, Object value) throws SearchException;

  /**
   * Is this type comparable?
   * 
   * @return true if this type is comparable
   */
  public boolean isComparable() {
    // some types might want to override this
    return true;
  }

  private static String type(Object value) {
    if (value == null) { return "null"; }
    return value.getClass().getName();
  }

  private static ToolkitAttributeType typeForOrNull(Class<?> c) {
    ToolkitAttributeType type = MAPPINGS.get(c);
    if (type != null) { return type; }

    return c.isEnum() ? ENUM : null;
  }

  static {
    MAPPINGS.put(Boolean.class, BOOLEAN);
    MAPPINGS.put(Byte.class, BYTE);
    MAPPINGS.put(Character.class, CHAR);
    MAPPINGS.put(Double.class, DOUBLE);
    MAPPINGS.put(Float.class, FLOAT);
    MAPPINGS.put(Integer.class, INT);
    MAPPINGS.put(Long.class, LONG);
    MAPPINGS.put(Short.class, SHORT);
    MAPPINGS.put(String.class, STRING);
    MAPPINGS.put(java.util.Date.class, DATE);
    MAPPINGS.put(java.sql.Date.class, SQL_DATE);
    MAPPINGS.put(char.class, CHAR);
    MAPPINGS.put(int.class, INT);
    MAPPINGS.put(long.class, LONG);
    MAPPINGS.put(byte.class, BYTE);
    MAPPINGS.put(boolean.class, BOOLEAN);
    MAPPINGS.put(float.class, FLOAT);
    MAPPINGS.put(double.class, DOUBLE);
    MAPPINGS.put(short.class, SHORT);
  }
}
