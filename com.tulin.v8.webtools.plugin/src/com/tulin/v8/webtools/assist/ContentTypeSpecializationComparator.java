package com.tulin.v8.webtools.assist;

import java.util.Comparator;

import org.eclipse.core.runtime.content.IContentType;

/**
 * Compares extension so that the ones with the most "specialized" content-types
 * are returned first.
 *
 * @param <T>
 */
public class ContentTypeSpecializationComparator<T> implements Comparator<ContentTypeRelatedExtension<T>> {

	@Override
	public int compare(ContentTypeRelatedExtension<T> o1, ContentTypeRelatedExtension<T> o2) {
		return depth(o2.targetContentType) - depth(o1.targetContentType);
	}

	public static int depth(IContentType targetContentType) {
		int res = 0;
		IContentType current = targetContentType;
		while (current != null) {
			res++;
			current = current.getBaseType();
		}
		return res;
	}

}
