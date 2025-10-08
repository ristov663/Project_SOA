import api from './api';
import { 
  Thesis, 
  CreateThesisForm, 
  UpdateThesisForm, 
  PaginatedResponse, 
  FilterOptions,
  ApiResponse 
} from '../types/thesis';

const ENDPOINTS = {
  THESES: '/theses',
  THESIS_BY_ID: (id: string) => `/theses/${id}`,
  THESIS_STATUS: (id: string) => `/theses/${id}/status`,
  THESIS_PHASES: (id: string) => `/theses/${id}/phases`,
  STUDENT_THESES: (studentId: string) => `/students/${studentId}/theses`,
  MENTOR_THESES: (mentorId: string) => `/mentors/${mentorId}/theses`,
};

export const thesisService = {
  // Get all theses with optional filtering
  async getAll(params?: FilterOptions): Promise<PaginatedResponse<Thesis>> {
    const response = await api.get(ENDPOINTS.THESES, { params });
    return response.data;
  },

  // Get thesis by ID
  async getById(id: string): Promise<Thesis> {
    const response = await api.get(ENDPOINTS.THESIS_BY_ID(id));
    return response.data.data;
  },

  // Create new thesis
  async create(data: CreateThesisForm): Promise<Thesis> {
    const response = await api.post(ENDPOINTS.THESES, data);
    return response.data.data;
  },

  // Update thesis
  async update(id: string, data: UpdateThesisForm): Promise<Thesis> {
    const response = await api.put(ENDPOINTS.THESIS_BY_ID(id), data);
    return response.data.data;
  },

  // Delete thesis
  async delete(id: string): Promise<void> {
    await api.delete(ENDPOINTS.THESIS_BY_ID(id));
  },

  // Update thesis status
  async updateStatus(id: string, status: string): Promise<Thesis> {
    const response = await api.patch(ENDPOINTS.THESIS_STATUS(id), { status });
    return response.data.data;
  },

  // Get theses by student
  async getByStudent(studentId: string): Promise<Thesis[]> {
    const response = await api.get(ENDPOINTS.STUDENT_THESES(studentId));
    return response.data.data;
  },

  // Get theses by mentor
  async getByMentor(mentorId: string): Promise<Thesis[]> {
    const response = await api.get(ENDPOINTS.MENTOR_THESES(mentorId));
    return response.data.data;
  },

  // Submit thesis for review
  async submit(id: string): Promise<Thesis> {
    const response = await api.post(`${ENDPOINTS.THESIS_BY_ID(id)}/submit`);
    return response.data.data;
  },

  // Approve thesis
  async approve(id: string): Promise<Thesis> {
    const response = await api.post(`${ENDPOINTS.THESIS_BY_ID(id)}/approve`);
    return response.data.data;
  },

  // Reject thesis
  async reject(id: string, reason: string): Promise<Thesis> {
    const response = await api.post(`${ENDPOINTS.THESIS_BY_ID(id)}/reject`, { reason });
    return response.data.data;
  },
};