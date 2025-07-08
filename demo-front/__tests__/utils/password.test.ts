import { validatePassword } from '../../utils/password';

describe('Password Validation Tests', () => {
  describe('Valid Passwords', () => {
    test('should accept password with all criteria met', () => {
      const password = 'Password123!';
      expect(validatePassword(password)).toBe(true);
    });

    test('should accept password with different special characters', () => {
      const password = 'Senha123@';
      expect(validatePassword(password)).toBe(true);
    });

    test('should accept password with exactly 8 characters', () => {
      const password = 'Pass1!@#';
      expect(validatePassword(password)).toBe(true);
    });

    test('should accept password with multiple special characters', () => {
      const password = 'Pass123!@#';
      expect(validatePassword(password)).toBe(true);
    });
  });

  describe('Invalid Passwords', () => {
    test('should reject password without special characters', () => {
      const password = 'Password123';
      expect(validatePassword(password)).toBe(false);
    });

    test('should reject password too short', () => {
      const password = 'Pass1!';
      expect(validatePassword(password)).toBe(false);
    });

    test('should reject password without numbers', () => {
      const password = 'Password!';
      expect(validatePassword(password)).toBe(false);
    });

    test('should reject password without uppercase letters', () => {
      const password = 'senha123!';
      expect(validatePassword(password)).toBe(false);
    });

    test('should reject password without lowercase letters', () => {
      const password = 'SENHA123!';
      expect(validatePassword(password)).toBe(false);
    });

    test('should reject empty password', () => {
      const password = '';
      expect(validatePassword(password)).toBe(false);
    });

    test('should reject password with only letters', () => {
      const password = 'Password';
      expect(validatePassword(password)).toBe(false);
    });

    test('should reject password with only numbers', () => {
      const password = '12345678';
      expect(validatePassword(password)).toBe(false);
    });
  });

  describe('Edge Cases', () => {
    test('should handle null input', () => {
      expect(validatePassword(null as any)).toBe(false);
    });

    test('should handle undefined input', () => {
      expect(validatePassword(undefined as any)).toBe(false);
    });

    test('should handle non-string input', () => {
      expect(validatePassword(123 as any)).toBe(false);
    });

    test('should handle password with spaces', () => {
      const password = 'Pass 123!';
      expect(validatePassword(password)).toBe(false);
    });
  });
}); 