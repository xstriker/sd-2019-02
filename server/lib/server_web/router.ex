defmodule ServerWeb.Router do
  use ServerWeb, :router

  pipeline :api do
    plug :accepts, ["json"]
  end

  scope "/curriculum", ServerWeb do
    pipe_through :api

    resources "/", CurriculumController, only: [:show, :create, :update]
    get "/", CurriculumController, :getFilter
  end

  scope "/job_opportunity", ServerWeb do
    pipe_through :api

    resources "/", JobOpportunityController, only: [:show, :create, :update]
    get "/", JobOpportunityController, :getFilter
  end
end
