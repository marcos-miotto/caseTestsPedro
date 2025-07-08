import axios from 'axios';
import authService from '../../service/auth';

// Mock do axios
const mockAxios = axios as jest.Mocked<typeof axios>;

describe('Auth Service Tests', () => {
  beforeEach(() => {
    jest.clearAllMocks();
  });

  describe('signIn', () => {
    test('should successfully sign in with valid credentials', async () => {
      const mockResponse = { data: { token: 'fake_token' } };
      mockAxios.post.mockResolvedValue(mockResponse);

      const result = await authService.signIn('test@example.com', 'Password123!');

      expect(mockAxios.post).toHaveBeenCalledWith('/auth/signin', {
        email: 'test@example.com',
        password: 'Password123!',
      });
      expect(result).toEqual({ token: 'fake_token' });
    });

    test('should throw error on sign in failure', async () => {
      const mockError = new Error('Invalid credentials');
      mockAxios.post.mockRejectedValue(mockError);

      await expect(authService.signIn('test@example.com', 'wrongpassword'))
        .rejects.toThrow('Invalid credentials');
    });
  });

  describe('signUp', () => {
    test('should successfully sign up with valid data', async () => {
      const mockUser = {
        id: 1,
        email: 'newuser@example.com',
        password: 'Password123!'
      };
      const mockResponse = { data: mockUser };
      mockAxios.post.mockResolvedValue(mockResponse);

      const result = await authService.signUp('newuser@example.com', 'Password123!');

      expect(mockAxios.post).toHaveBeenCalledWith('/auth/signup', {
        email: 'newuser@example.com',
        password: 'Password123!',
      });
      expect(result).toEqual(mockUser);
    });

    test('should throw error on sign up failure', async () => {
      const mockError = new Error('Email already exists');
      mockAxios.post.mockRejectedValue(mockError);

      await expect(authService.signUp('existing@example.com', 'Password123!'))
        .rejects.toThrow('Email already exists');
    });
  });

  describe('resetPassword', () => {
    test('should successfully reset password', async () => {
      const mockResponse = { data: { message: 'Password reset successfully' } };
      mockAxios.post.mockResolvedValue(mockResponse);

      const result = await authService.resetPassword('user@example.com');

      expect(mockAxios.post).toHaveBeenCalledWith('/auth/reset-password', {
        email: 'user@example.com',
      });
      expect(result).toEqual({ message: 'Password reset successfully' });
    });

    test('should throw error on password reset failure', async () => {
      const mockError = new Error('User not found');
      mockAxios.post.mockRejectedValue(mockError);

      await expect(authService.resetPassword('nonexistent@example.com'))
        .rejects.toThrow('User not found');
    });
  });
}); 