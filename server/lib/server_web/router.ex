defmodule ServerWeb.Router do
  use ServerWeb, :router

  pipeline :api do
    plug :accepts, ["json"]
  end

  @doc """
  Para o path de currículos e vagas foram declaradas as rotas POST e PUT padrões do REST e substituido o GET padrão por uma com query params
  """
  scope "/curriculum", ServerWeb do
    pipe_through :api

    resources "/", CurriculumController, only: [:create, :update]
    get "/", CurriculumController, :getFilter
  end

  scope "/job_opportunity", ServerWeb do
    pipe_through :api

    resources "/", JobOpportunityController, only: [:create, :update]
    get "/", JobOpportunityController, :getFilter
  end
end
