"use client";

import React from "react";
import { Form, Input, Button } from "@heroui/react";
import { useRouter } from "next/navigation";
import authService from "@/service/auth";
import { validateEmail } from "@/utils/email";

export default function SignIn() {
  const [isPasswordInvalid, setIsPasswordInvalid] = React.useState(false);
  const [isEmailInvalid, setIsEmailInvalid] = React.useState(false);
  const [isFormInvalid, setIsFormInvalid] = React.useState(false);

  const [emailErrorMessage, setEmailErrorMessage] = React.useState<
    string | null
  >(null);
  const [passwordErrorMessage, setPasswordErrorMessage] = React.useState<
    string | null
  >(null);

  const router = useRouter();

  function clearErrors() {
    setIsPasswordInvalid(false);
    setIsEmailInvalid(false);
    setIsFormInvalid(false);
  }

  return (
    <main className="flex min-h-screen flex-col items-center justify-center p-24">
      <h1 className="text-3xl font-bold mb-8">Bem-vindo ao site!</h1>
      <Form
        validationBehavior="aria"
        className="w-full max-w-xs flex flex-col gap-4"
        onReset={() => router.push("/reset-password")}
        onSubmit={async (e) => {
          e.preventDefault();
          let data = Object.fromEntries(new FormData(e.currentTarget));
          if (!validateEmail(data.email as string)) {
            setIsEmailInvalid(true);
            setEmailErrorMessage("E-mail inválido");
            return;
          }
          try {
            const response = await authService.signIn(
              data.email as string,
              data.password as string
            );
            if (response.token) {
              return router.push("/auth/home");
            }
          } catch (_) {
            setEmailErrorMessage("Credenciais inválidas");
            setPasswordErrorMessage("Credenciais inválidas");
            setIsFormInvalid(true);
          }
        }}
      >
        <Input
          onValueChange={() => clearErrors()}
          isRequired
          label="Email"
          labelPlacement="outside"
          name="email"
          placeholder="Entre seu email"
          type="email"
          isInvalid={isFormInvalid || isEmailInvalid}
          errorMessage={emailErrorMessage}
        />

        <Input
          onValueChange={() => clearErrors()}
          isRequired
          label="Password"
          labelPlacement="outside"
          name="password"
          placeholder="Senha"
          type="password"
          isInvalid={isFormInvalid || isPasswordInvalid}
          errorMessage={passwordErrorMessage}
        />
        <div className="flex flex-col gap-4 w-full">
          <Button className="h-10" color="primary" type="submit">
            Acessar
          </Button>
          <div className="flex gap-2 flex-1 w-full">
            <Button
              className="h-10 flex-1"
              type="button"
              color="secondary"
              variant="ghost"
              onPress={() => router.push("/signup")}
            >
              Criar conta nova
            </Button>
            <Button className="h-10 flex-1" type="reset" variant="ghost">
              Esqueci a senha
            </Button>
          </div>
        </div>
      </Form>
    </main>
  );
}
