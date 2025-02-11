import type { Template, TemplateEditRequest, TemplateListResponse, TemplateUploadRequest } from '@/types';
import { customFetch } from './customFetch';

const API_URL = process.env.REACT_APP_API_URL;

export const TEMPLATE_API_URL = `${API_URL}/templates`;

export const getTemplateList = async (
  categoryId?: number,
  tagId?: number,
  page: number = 1,
  pageSize: number = 20,
): Promise<TemplateListResponse> => {
  const url = new URL(TEMPLATE_API_URL);

  if (categoryId) {
    url.searchParams.append('categoryId', categoryId.toString());
  }

  if (tagId) {
    url.searchParams.append('tags', tagId.toString());
  }

  url.searchParams.append('pageNumber', page.toString());
  url.searchParams.append('pageSize', pageSize.toString());

  return await customFetch({
    url: url.toString(),
  });
};

export const getTemplate = async (id: number): Promise<Template> =>
  await customFetch({
    url: `${TEMPLATE_API_URL}/${id}`,
  });

export const postTemplate = async (newTemplate: TemplateUploadRequest) =>
  await customFetch({
    method: 'POST',
    url: `${TEMPLATE_API_URL}`,
    body: JSON.stringify(newTemplate),
  });

export const editTemplate = async ({ id, template }: { id: number; template: TemplateEditRequest }): Promise<void> => {
  await customFetch({
    method: 'POST',
    url: `${TEMPLATE_API_URL}/${id}`,
    body: JSON.stringify(template),
  });
};

export const deleteTemplate = async (id: number): Promise<void> => {
  await customFetch({
    method: 'DELETE',
    url: `${TEMPLATE_API_URL}/${id}`,
  });
};
