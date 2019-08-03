package org.w3c.dom;

/**
 * @Auther: yaojc
 * @Date: 2019/7/17
 * @Description: 此处定义此类是为了解决问题：java.lang.NoClassDefFoundError: org/w3c/dom/ElementTraversal
 */
public interface ElementTraversal {

    Element getFirstElementChild();

    Element getLastElementChild();

    Element getPreviousElementSibling();

    Element getNextElementSibling();

    int getChildElementCount();

}
