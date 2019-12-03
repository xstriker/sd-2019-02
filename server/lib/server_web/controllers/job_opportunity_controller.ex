defmodule ServerWeb.JobOpportunityController do
  use ServerWeb, :controller

  import Ecto.Query, only: [from: 2]

  alias Server.JobServer
  alias Server.JobServer.JobOpportunity
  alias Server.Repo

  action_fallback ServerWeb.FallbackController

  @doc """
  Método para criação de uma nova vaga - recebe um json com o objeto vaga e retorna uma mensagem de sucesso
  """
  def create(conn, %{"job_opportunity" => job_opportunity_params}) do
    with {:ok, %JobOpportunity{} = job_opportunity} <- JobServer.create_job_opportunity(job_opportunity_params) do
      conn
      |> put_status(:created)
      |> put_resp_header("location", Routes.job_opportunity_path(conn, :show, job_opportunity))
      |> text("Inserido com sucesso")
    end
  end

  @doc """
  Método para a atualizacao de uma vaga - recebe o ID e a vaga atualizada e retorna uma mensagem de sucesso
  """
  def update(conn, %{"id" => id, "job_opportunity" => job_opportunity_params}) do
    job_opportunity = JobServer.get_job_opportunity!(id)

    with {:ok, %JobOpportunity{} = _job_opportunity} <- JobServer.update_job_opportunity(job_opportunity, job_opportunity_params) do
      text(conn, "Atualizado com sucesso")
    end
  end

  @doc """
  Método para listagem de vagas - recebe a área e o salário como filtros e retorna uma lista de vagas
  """
  def getFilter(conn, %{"area" => area, "salario" => salario}) do
    list = Repo.all(from u in JobOpportunity, where: u.area == ^area and u.salario >= ^salario)
    json(conn, %{"vagas" => list})
  end
end
