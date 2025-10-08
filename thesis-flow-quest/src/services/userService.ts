import api from './api';
import { User, Student, Professor, Administrator, CreateUserForm, PaginatedResponse } from '../types/thesis';

const ENDPOINTS = {
  USERS: '/users',
  STUDENTS: '/students',
  PROFESSORS: '/professors',
  ADMINISTRATORS: '/administrators',
  USER_BY_ID: (id: string) => `/users/${id}`,
  STUDENT_BY_ID: (id: string) => `/students/${id}`,
  PROFESSOR_BY_ID: (id: string) => `/professors/${id}`,
  ADMIN_BY_ID: (id: string) => `/administrators/${id}`,
};

export const userService = {
  // Users
  async getAllUsers(params?: any): Promise<PaginatedResponse<User>> {
    const response = await api.get(ENDPOINTS.USERS, { params });
    return response.data;
  },

  async getUserById(id: string): Promise<User> {
    const response = await api.get(ENDPOINTS.USER_BY_ID(id));
    return response.data.data;
  },

  async createUser(data: CreateUserForm): Promise<User> {
    const response = await api.post(ENDPOINTS.USERS, data);
    return response.data.data;
  },

  async updateUser(id: string, data: Partial<CreateUserForm>): Promise<User> {
    const response = await api.put(ENDPOINTS.USER_BY_ID(id), data);
    return response.data.data;
  },

  async deleteUser(id: string): Promise<void> {
    await api.delete(ENDPOINTS.USER_BY_ID(id));
  },

  // Students
  async getAllStudents(params?: any): Promise<PaginatedResponse<Student>> {
    const response = await api.get(ENDPOINTS.STUDENTS, { params });
    return response.data;
  },

  async getStudentById(id: string): Promise<Student> {
    const response = await api.get(ENDPOINTS.STUDENT_BY_ID(id));
    return response.data.data;
  },

  async createStudent(data: CreateUserForm): Promise<Student> {
    const response = await api.post(ENDPOINTS.STUDENTS, data);
    return response.data.data;
  },

  async updateStudent(id: string, data: Partial<CreateUserForm>): Promise<Student> {
    const response = await api.put(ENDPOINTS.STUDENT_BY_ID(id), data);
    return response.data.data;
  },

  // Professors
  async getAllProfessors(params?: any): Promise<PaginatedResponse<Professor>> {
    const response = await api.get(ENDPOINTS.PROFESSORS, { params });
    return response.data;
  },

  async getProfessorById(id: string): Promise<Professor> {
    const response = await api.get(ENDPOINTS.PROFESSOR_BY_ID(id));
    return response.data.data;
  },

  async createProfessor(data: CreateUserForm): Promise<Professor> {
    const response = await api.post(ENDPOINTS.PROFESSORS, data);
    return response.data.data;
  },

  async updateProfessor(id: string, data: Partial<CreateUserForm>): Promise<Professor> {
    const response = await api.put(ENDPOINTS.PROFESSOR_BY_ID(id), data);
    return response.data.data;
  },

  // Administrators
  async getAllAdministrators(params?: any): Promise<PaginatedResponse<Administrator>> {
    const response = await api.get(ENDPOINTS.ADMINISTRATORS, { params });
    return response.data;
  },

  async getAdministratorById(id: string): Promise<Administrator> {
    const response = await api.get(ENDPOINTS.ADMIN_BY_ID(id));
    return response.data.data;
  },

  async createAdministrator(data: CreateUserForm): Promise<Administrator> {
    const response = await api.post(ENDPOINTS.ADMINISTRATORS, data);
    return response.data.data;
  },

  async updateAdministrator(id: string, data: Partial<CreateUserForm>): Promise<Administrator> {
    const response = await api.put(ENDPOINTS.ADMIN_BY_ID(id), data);
    return response.data.data;
  },
};