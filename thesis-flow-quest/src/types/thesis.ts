// Core thesis management types

export interface User {
  id: string;
  firstName: string;
  lastName: string;
  email: string;
  role: 'STUDENT' | 'PROFESSOR' | 'ADMINISTRATOR';
  department?: string;
  createdAt: string;
  updatedAt: string;
}

export interface Student extends User {
  role: 'STUDENT';
  indexNumber: string;
  year: number;
  gpa?: number;
}

export interface Professor extends User {
  role: 'PROFESSOR';
  title: string;
  specialization: string;
  availableSlots: number;
}

export interface Administrator extends User {
  role: 'ADMINISTRATOR';
  permissions: string[];
}

export interface Thesis {
  id: string;
  title: string;
  description: string;
  status: ThesisStatus;
  studentId: string;
  mentorId: string;
  proposedDate: string;
  approvedDate?: string;
  submittedDate?: string;
  defenseDate?: string;
  finalGrade?: number;
  keywords: string[];
  abstract?: string;
  createdAt: string;
  updatedAt: string;
}

export type ThesisStatus = 
  | 'PROPOSED' 
  | 'APPROVED' 
  | 'IN_PROGRESS' 
  | 'SUBMITTED' 
  | 'UNDER_REVIEW' 
  | 'DEFENDED' 
  | 'ARCHIVED' 
  | 'REJECTED';

export interface ThesisPhase {
  id: string;
  thesisId: string;
  phase: string;
  status: 'PENDING' | 'IN_PROGRESS' | 'COMPLETED' | 'OVERDUE';
  deadline: string;
  completedDate?: string;
  description: string;
  requirements: string[];
}

export interface Defense {
  id: string;
  thesisId: string;
  date: string;
  location: string;
  committee: string[];
  status: 'SCHEDULED' | 'COMPLETED' | 'CANCELLED';
  grade?: number;
  feedback?: string;
}

export interface Notification {
  id: string;
  userId: string;
  type: 'DEADLINE' | 'STATUS_CHANGE' | 'DEFENSE' | 'APPROVAL' | 'GENERAL';
  title: string;
  message: string;
  isRead: boolean;
  createdAt: string;
  actionUrl?: string;
}

// API Response types
export interface ApiResponse<T> {
  data: T;
  message?: string;
  success: boolean;
}

export interface PaginatedResponse<T> {
  data: T[];
  total: number;
  page: number;
  pageSize: number;
  totalPages: number;
}

// Form types
export interface CreateThesisForm {
  title: string;
  description: string;
  mentorId: string;
  keywords: string[];
  abstract?: string;
}

export interface UpdateThesisForm extends Partial<CreateThesisForm> {
  status?: ThesisStatus;
}

export interface CreateUserForm {
  firstName: string;
  lastName: string;
  email: string;
  role: 'STUDENT' | 'PROFESSOR' | 'ADMINISTRATOR';
  department?: string;
  indexNumber?: string; // for students
  title?: string; // for professors
  specialization?: string; // for professors
}

export interface FilterOptions {
  status?: ThesisStatus[];
  mentorId?: string;
  studentId?: string;
  department?: string;
  year?: number;
  search?: string;
  sortBy?: string;
  sortOrder?: 'asc' | 'desc';
}