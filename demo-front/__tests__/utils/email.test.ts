import { validateEmail } from '../../utils/email';

describe('Email Validation Tests', () => {
  describe('Valid Emails', () => {
    test('should accept standard email format', () => {
      const email = 'usuario@exemplo.com';
      expect(validateEmail(email)).toBe(true);
    });

    test('should accept email with subdomain', () => {
      const email = 'usuario@sub.exemplo.com';
      expect(validateEmail(email)).toBe(true);
    });

    test('should accept email with numbers', () => {
      const email = 'user123@exemplo.com';
      expect(validateEmail(email)).toBe(true);
    });

    test('should accept email with underscore', () => {
      const email = 'user_name@exemplo.com';
      expect(validateEmail(email)).toBe(true);
    });

    test('should accept email with dot in name', () => {
      const email = 'user.name@exemplo.com';
      expect(validateEmail(email)).toBe(true);
    });

    test('should accept email with uppercase', () => {
      const email = 'USER@EXEMPLO.COM';
      expect(validateEmail(email)).toBe(true);
    });
  });

  describe('Invalid Emails', () => {
    test('should reject email without @', () => {
      const email = 'usuarioexemplo.com';
      expect(validateEmail(email)).toBe(false);
    });

    test('should reject email without domain', () => {
      const email = 'usuario@';
      expect(validateEmail(email)).toBe(false);
    });

    test('should reject email without username', () => {
      const email = '@exemplo.com';
      expect(validateEmail(email)).toBe(false);
    });

    test('should reject empty email', () => {
      const email = '';
      expect(validateEmail(email)).toBe(false);
    });

    test('should reject email with spaces', () => {
      const email = 'usuario @exemplo.com';
      expect(validateEmail(email)).toBe(false);
    });

    test('should reject email with multiple @', () => {
      const email = 'usuario@@exemplo.com';
      expect(validateEmail(email)).toBe(false);
    });

    test('should reject email with only @', () => {
      const email = '@';
      expect(validateEmail(email)).toBe(false);
    });

    test('should reject email with special characters in domain', () => {
      const email = 'usuario@exemplo!.com';
      expect(validateEmail(email)).toBe(false);
    });
  });

  describe('Edge Cases', () => {
    test('should handle null input', () => {
      expect(validateEmail(null as any)).toBe(false);
    });

    test('should handle undefined input', () => {
      expect(validateEmail(undefined as any)).toBe(false);
    });

    test('should handle non-string input', () => {
      expect(validateEmail(123 as any)).toBe(false);
    });
  });
}); 