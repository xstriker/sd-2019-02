defmodule ServerWeb.JobOpportunityController do
  use ServerWeb, :controller

  import Ecto.Query, only: [from: 2]

  alias Server.JobServer
  alias Server.JobServer.JobOpportunity
  alias Server.Repo

  action_fallback ServerWeb.FallbackController

  def index(conn, _params) do
    opportunities = JobServer.list_opportunities()
    render(conn, "index.json", opportunities: opportunities)
  end

  def create(conn, %{"job_opportunity" => job_opportunity_params}) do
    with {:ok, %JobOpportunity{} = job_opportunity} <- JobServer.create_job_opportunity(job_opportunity_params) do
      conn
      |> put_status(:created)
      |> put_resp_header("location", Routes.job_opportunity_path(conn, :show, job_opportunity))
      |> text("Inserido com sucesso")
    end
  end

  def show(conn, %{"id" => id}) do
    job_opportunity = JobServer.get_job_opportunity!(id)
    render(conn, "show.json", job_opportunity: job_opportunity)
  end

  def update(conn, %{"id" => id, "job_opportunity" => job_opportunity_params}) do
    job_opportunity = JobServer.get_job_opportunity!(id)

    with {:ok, %JobOpportunity{} = job_opportunity} <- JobServer.update_job_opportunity(job_opportunity, job_opportunity_params) do
      text(conn, "Atualizado com sucesso")
    end
  end

  def delete(conn, %{"id" => id}) do
    job_opportunity = JobServer.get_job_opportunity!(id)

    with {:ok, %JobOpportunity{}} <- JobServer.delete_job_opportunity(job_opportunity) do
      send_resp(conn, :no_content, "")
    end
  end

  def getFilter(conn, %{"area" => area, "salario" => salario}) do
    list = Repo.all(from u in JobOpportunity, where: u.area == ^area and u.salario == ^salario)
    json(conn, %{"vagas" => list.rows})
  end
end
