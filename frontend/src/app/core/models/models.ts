export interface AdminEmployeeStats {
    EmployeeCount: number;
    onlineEmployeeCount: number;
}

export interface AdminRequestsStats {
    aiHandledPercentage: number;
    employeeHandledPercentage: number;
    inProgressRequestsCount: number;
    unassignedRequestsCount: number;
}

export interface AIProcessedRequestPercentageChart {
    percentage: number;
    dateTime: string; 
}

export interface AuthDto {
    jwt: string;
    role: Role;
}

export interface DailyPercentageOfRequestsHandledByAIChartData {
    percentage: number;
    dateTime: string;
}

export interface EmployeeDto {
    id: number;
    fullName: string;
    inProgressRequestCount: number;
    ClosedRequestCount: number;
    isOnline: boolean;
}

export interface EmployeeStats {
    totalRequestsHandled: number;
    inProgressRequestsCount: number;
    closedRequestsCountToday: number;
    closedRequestsCountThisWeek: number;
    closedRequestsCountThisMonth: number;
}

export interface MessageDto {
    messageText: string;
    createdAt: string;
    side: Side;
    isHelpful?: boolean;
}

export interface ProfilePictureDto {
    fileName: string;
    fileBytes: string[];
}

export interface SignInDto {
    username: string;
    password: string;
}

export interface SignUpData {
    email: string;
    fullName: string;
    role: Role;
}

export interface TechSupportRequestDto {
    title: string;
    id: string;
    isInProgress: boolean;
    assignees: string[];
}

export interface UserProfileDto {
    fullName: string;
    roleName: string;
    email: string
}

export enum Side {
    USER = "USER",
    BOT = "BOT",
    TECH_SUPPORT_EMPLOYEE="TECH_SUPPORT_EMPLOYEE"
}

export enum EmployeeRole {
    ADMIN,
    TECH_SUPPORT_EMPLOYEE
}
export enum Role {
    ADMIN = "ADMIN",
    TECH_SUPPORT_EMPLOYEE = "TECH_SUPPORT_EMPLOYEE"
}
export interface SendMessage {
    text: string,
    isEditedByTechSupport: boolean
}